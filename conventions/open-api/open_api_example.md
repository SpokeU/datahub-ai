Below is the example of OpenAPI spec. Please use it as a reference when creating your own. Also pay attention to Key points section.

```yaml
openapi: 3.0.1
info:
  title: Instructions Processing API
  version: 1.0.0
  description: This API is intended to be used by LLM to act as a bridge to outside world
servers:
  - url: 'http://localhost:8080'
paths:
  /instructions-processing:
    $ref: './paths/instructions_processing.yaml'
```


Key points:
- Main file is named - `open_api_spec.yaml` and placed according to project structure
- All paths are externalized to `api/paths` folder
- All 'schemas' are externalized to `api/schemas` folder and referenced using '$ref'
- Enums are externalized to `api/enums` folder
- Use a pattern where there is a common model (usually its for request) and response is a combination of this common model plus generated fields like `id`, `createdAt` etc..
- Add examples to schemas 
- Don't add 'schemas' to 'components:' section as they are referenced when used
- Reuse and externalize schemas
