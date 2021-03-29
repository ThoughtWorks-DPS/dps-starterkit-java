package authz

default allow = false

allow {
  input.method == "GET"
  startswith(input.path, "/v1/{{cookiecutter.PKG_SERVICE_NAME}}/{{cookiecutter.PKG_RESOURCE_NAME}}s")
#  input.subject.authorities[_] == "ROLE_HR"
}

allow {
  input.method == "PUT"
  startswith(input.path, "/v1/{{cookiecutter.PKG_SERVICE_NAME}}/{{cookiecutter.PKG_RESOURCE_NAME}}s")
#  input.subject.authorities[_] == "ROLE_ADMIN"
}

allow {
  input.method == "POST"
  startswith(input.path, "/v1/{{cookiecutter.PKG_SERVICE_NAME}}/{{cookiecutter.PKG_RESOURCE_NAME}}s")
#  input.subject.authorities[_] == "ROLE_ADMIN"
}

allow {
  input.method == "DELETE"
  startswith(input.path, "/v1/{{cookiecutter.PKG_SERVICE_NAME}}/{{cookiecutter.PKG_RESOURCE_NAME}}s")
#  input.subject.authorities[_] == "ROLE_ADMIN"
}
