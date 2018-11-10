# Mercadolibre Test REST API

What This Is
------------

This is the implementation to mercadolibre interview exercise.

It consists on a REST API developed in java with spring boot that allows to validate DNAs to determine if it is mutants or humans.

To Install
---------

    git clone https://github.com/fonchi/mltest-api.git
    cd mltest-api
    mvn clean install

To Run
------

    mvn spring-boot:run

## Request & Response Examples

### API Resources

  - [POST /dnas/mutant](#post-dnasmutant)
  
### POST /dnas/mutant

Example: POST  https://mercadolibre-test.azurewebsites.net/api/dnas/mutant

#### Mutant DNA (valid)
Request body:
    
    {
        “dna”:["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
    }

Response: HTTP 200-OK

#### Human DNA (invalid)
Request body:
    
    {
        “dna”:["CGTACA", "CACGTA", "CGTACA", "ACACGT", "GTACAC", "TACACG"]
    }

Response: HTTP 403-Forbidden