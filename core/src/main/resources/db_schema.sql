
DROP TABLE meta_attribute;
DROP TYPE attribute_type;
CREATE TYPE attribute_type AS ENUM ('int', 'double', 'string', 'text', 'boolean','timestamp');

CREATE TABLE meta_attribute (
   uuid varchar(50) PRIMARY KEY,
   class_type varchar(50) default 'attribute',
   name VARCHAR (50) UNIQUE NOT NULL,
   attr_type  attribute_type NOT NULL,
   range jsonb,
   format VARCHAR(50), -- for time stamp format
   created_by VARCHAR (50) UNIQUE NOT NULL,
   created_on TIMESTAMP NOT NULL,
   last_modified_on TIMESTAMP NOT NULL
);

ALTER TABLE meta_attribute 
   ADD CONSTRAINT check_class_types 
   CHECK (class_type = 'attribute');

CREATE TABLE meta_type (
    uuid varchar(),
)






ALTER TABLE meta_attribute ADD CONSTRAINT attr_range_is_valid CHECK (
  validate_json_schema($$
    {
  "$schema": "http://json-schema.org/draft-04/schema#",
  "type": "object",
  "properties": {
    "options": {
      "type": "array",
      "items": {}
    },
    "between": {
      "type": "object",
      "properties": {
        "start": {
          "type": "integer"
        },
        "end": {
          "type": "integer"
        }
      },
      "required": [
        "start",
        "end"
      ]
    },
    "between_with": {
      "type": "object",
      "properties": {
        "start": {
          "type": "integer"
        },
        "end": {
          "type": "integer"
        }
      },
      "required": [
        "start",
        "end"
      ]
    }
  },
  "anyOf": [
    "options",
    "between",
    "between_with"
  ]
}
  $$, range)
);

-- { options: [], - string, int, double,
--  between:{ - int, timestamp
--      "start":1,
--      "end":'20'
--      },
--  between_with: { - int, timestamp
--      "start":1,
--      "end":'20'
--      },
--  }