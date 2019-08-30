
--DROP DATABASE IF EXISTS nmallavaram;
CREATE DATABASE core;
CREATE USER core_admin WITH ENCRYPTED PASSWORD 'admin123';
GRANT ALL PRIVILEGES ON DATABASE core TO core_admin;
GRANT USAGE ON SCHEMA public TO core_admin;
DROP TABLE meta_attribute;
DROP TYPE attribute_type;
--CREATE TYPE attribute_type AS ENUM ('int', 'double', 'string', 'text', 'boolean','timestamp','table','graph', 'image', 'video');

CREATE TABLE meta_attribute (
   uuid varchar(50) PRIMARY KEY,
   class_type varchar(50) default 'attribute',
   name VARCHAR (50) UNIQUE NOT NULL,
   attr_type  varchar(15) NOT NULL,
   range_def jsonb,
   table_def jsonb,
   graph_def jsonb,
   is_array BOOLEAN NOT NULL default false, -- if this attributes holds array of values
   format VARCHAR(50), -- for time stamp format
   created_by VARCHAR (50)  NOT NULL,
   created_date TIMESTAMP NOT NULL,
   last_modified_by VARCHAR (50) NOT NULL,
   last_modified_date TIMESTAMP NOT NULL
);

ALTER TABLE meta_attribute 
   ADD CONSTRAINT check_class_types 
   CHECK (class_type = 'attribute');

ALTER TABLE meta_attribute 
   ADD CONSTRAINT check_attribute_types 
   CHECK (attr_type = 'attribute');

CREATE TABLE meta_type (
    uuid varchar()
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
  "script": {
      "type": "array",
      "items": {}
    },
  "anyOf": [
    "options",
    "between",
    "between_with",
    "script"
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