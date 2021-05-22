CREATE TABLE {{cookiecutter.PKG_SERVICE_NAME}}.{{cookiecutter.RESOURCE_TABLE_NAME}} (
    id VARCHAR(128) NOT NULL PRIMARY KEY,
    userName VARCHAR NOT NULL,
    pii VARCHAR NOT NULL,
    firstName VARCHAR NOT NULL,
    lastName VARCHAR NOT NULL
{%- if cookiecutter.CREATE_PARENT_RESOURCE == "y" %},
    {{cookiecutter.PKG_PARENT_RESOURCE_NAME}}Id VARCHAR NOT NULL
{%- endif %}
);
