# Model Generator

SlotTool subproject that generates data-models for different languages
(currently B and Prolog) from a sqlite database containing timetable
information based on the schema defined in
[schema.sql](http://gitlab.cobra.cs.uni-duesseldorf.de/slottool/models/blob/68c3429aba0d937ded78010f3193ff1fe08744af/schema.sql)

## Usage

The ```Model Generator``` can be used either as a library or as a commandline tool.

### Commandline
To create a datamodel on the commandline you have to provide the path to the database-file containing the data to be used, the path to the output file, the type of file to be generated and the faculty for which to generated the data model

```
usage: java -jar JARFILE --format=<format> --faculty=<faculty>
            --output=<path>
 -d,--database <arg>   SQLite Database
    --faculty <arg>    Faculty to generate data for: [wiwi, philfak]
    --format           Output format [b, prolog]
 -o,--output <arg>     Target file

```

### Library
Comming soon to a README in your favorite repository.

## Building

This project creates to artifacts, one jar-file containing only the library (```./gradlew jar``` to build) and a "fat"-jar that also contains all dependencies and can be directly invoked on the commandline without adding all dependencies to the classpath (```./gradlew fatJar``` to build).

## Deployment

Created jar files are currently deployed to the local maven repository.


```
./gradlew publish
```