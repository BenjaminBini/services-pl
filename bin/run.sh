#!/bin/bash

/wait-for-it.sh -t 0 mysql:3306 -- java -jar /app.jar