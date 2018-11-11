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

  - [POST /dnas/mutant](#post-/dnas/mutant) (1 and 2 versions)
  - [GET /dnas/stats](#get-/dnas/stats)
  
### POST /dnas/mutant
Example: POST  https://mercadolibre-test.azurewebsites.net/api/dnas/mutant
- Headers:
    - X-API-Version = 1 (it uses endpoint that only validates DNA)
    - X-API-Version = 2 (it uses endpoint that validates DNA and saves in database)

##### Valid DNA
Request body:
    
    {
        "dna":["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
    }

Response: *HTTP 200-OK*

##### Invalid DNA
Request body:
    
    {
        "dna":["CGTACA", "CACGTA", "CGTACA", "ACACGT", "GTACAC", "TACACG"]
    }

Response: *HTTP 403-Forbidden*

### GET /dnas/stats
Example: GET  https://mercadolibre-test.azurewebsites.net/api/dnas/stats
- Headers:
    - X-API-Version = 2 (required)

Response: *HTTP 200-OK*
    
    {
        "count_mutant_dna": 40,
        "count_human_dna": 100,
        "ratio": 0.4
    }