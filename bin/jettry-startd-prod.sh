#!/bin/bash
nohup mvn -Dmaven.test.skip -Pprod jetty:run > /dev/null 2>&1 &