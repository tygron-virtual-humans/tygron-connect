package nl.tudelft.contextproject.tygron.api.actions;

import nl.tudelft.contextproject.tygron.api.CallType;
import nl.tudelft.contextproject.tygron.api.Environment;
import nl.tudelft.contextproject.tygron.api.HttpConnection;
import nl.tudelft.contextproject.tygron.handlers.StringResultHandler;
import nl.tudelft.contextproject.tygron.handlers.objects.ServerWordsResultHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HttpConnection.class)
public class GiveMoneyActionTest {
  GiveMoneyAction action;

  @Mock
  HttpConnection connection;

  @Mock
  Environment environmentWithMoney;

  @Mock
  Environment environmentNoMoney;

  @Before
  public void setup() {
    PowerMockito.mockStatic(HttpConnection.class);
    BDDMockito.given(HttpConnection.getInstance()).willReturn(connection);

    Mockito.when(environmentNoMoney.getBudget(Mockito.anyInt())).thenReturn(0d);
    Mockito.when(environmentWithMoney.getBudget(Mockito.anyInt())).thenReturn(Double.MAX_VALUE);

    Mockito.when(connection.execute(anyString(), eq(CallType.GET),
            any(ServerWordsResultHandler.class), eq(true))).thenReturn(null);
  }

  @Test
  public void testActionWithMoney() {
    action = new GiveMoneyAction(environmentWithMoney);
    action.giveMoney(1, 10d);
    Mockito.verify(connection).execute(eq("event/PlayerEventType/MONEY_TRANSFER_GIVE/"), eq(CallType.POST),
            any(StringResultHandler.class), eq(true), any(AskMoneyAction.AskMoneyRequest.class));
  }

  @Test
  public void testActionNoMoney() {
    action = new GiveMoneyAction(environmentNoMoney);
    action.giveMoney(1, 10d);
    Mockito.verifyZeroInteractions(connection);
  }
}