# tygron-connect 

[![Build Status](https://travis-ci.org/tygron-virtual-humans/tygron-connect.svg)](https://travis-ci.org/tygron-virtual-humans/tygron-connect) Travis CI

[![Build Status](http://jenkins.buildwise.eu/job/tygron-connect/badge/icon)](http://jenkins.buildwise.eu/job/tygron-connect) Jenkins CI (Private)

`tygron-connect` is a project by a group of students at the Technical University of Delft, as a part of the second year's course TI2806 Contextproject. Students are expected to learn skills that are necesscary for software development projects for third parties. 

The project deliverable is a connector between GOAL and Tygron's interface. This project is a part of context-virtual-humans, and is a part of the groups' shared objective of creating a virtual human for the tygron engine. http://www.tygronengine.com/

##Install instructions

1. Download or build a `tygron-connect` environment.
  * **Jenkins CI build**  
    Jenkins provides automatic building for this project. Download [the latest jar with dependencies](http://jenkins.buildwise.eu/job/tygron-connect/lastBuild//tygron-connect$tygron-connect-environment/).
  * **Manual build**  
    To build tygron-connect manually, run `mvn deploy`.
2. This jar should be used as the environment in your GOAL `mas2g` file ([example](https://github.com/tygron-virtual-humans/tygron-connect/blob/master/tygron-connect-environment/src/GOAL/GoalTestTygron.mas2g)).
3. Add a `configuration.cfg` in the same folder as the environment jar. The file should specify a username and password ([example](https://github.com/tygron-virtual-humans/tygron-connect/blob/master/tygron-connect-environment/src/main/resources/testconfiguration.cfg)).
