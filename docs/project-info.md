## Technology overview
Language: Java 17  
Technologies: Spring Boot, JPA, Flyway
Build tools: Maven
Build process: 
- `openapi-generator-maven-plugin` Generates controllers based on open_api_spec.yaml into ${project.basedir}/src/main/resources/api/open_api_spec.yaml.
  - Generates API controllers into the package where endpoints are defined
  - Models into package where api models are defined
  - Uses delegatePattern option 