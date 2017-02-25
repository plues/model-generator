# Model Generator

[![Build Status](https://travis-ci.org/plues/model-generator.svg?branch=develop)](https://travis-ci.org/plues/model-generator)
[![Dependency Status](https://www.versioneye.com/user/projects/57a33bea1dadcb004f08e56a/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57a33bea1dadcb004f08e56a)
[![codebeat badge](https://codebeat.co/badges/515aa29f-75bb-4dfa-9a93-826ebb4fbecb)](https://codebeat.co/projects/github-com-plues-model-generator)

This tool is used to generate text representations of timetable data to be
used by different tools. `model-generator` is part of the `PlüS` project.

It takes a SQLite database using schema version 2 as input plus an optional
format or template use to generate the text represetation.

Current version is `4.6.0-SNAPSHOT`.

## Usage

The tool can be used as a library (see Server project) or as commandline tool.

### Commandline

To create a text version of the data on the commandline you have to provide the
path to the database-file containing the data to be used, the path to the
output file, the type of file to be generated and the faculty for which to
generated the data model.

```
usage: java -jar JARFILE --format=<format> --faculty=<faculty>
            --output=<path>
 -d,--database <arg>   SQLite Database
    --faculty <arg>    Faculty to generate data for: [wiwi, philfak]
    --format           Output format [b, prolog]
 -o,--output <arg>     Target file
```


## Building

This project creates two artifacts, one `jar`-file containing only the library
and a "fat"-`jar` that also contains all dependencies so it can be directly
invoked on the commandline.

- library: `./gradlew jar`
- standalone: `./gradlew buildStandaloneJar`


## Downloads

Both versions of the `jar`-files can be downloaded [here](http://www3.hhu.de/stups/downloads/plues/model-generator/).
