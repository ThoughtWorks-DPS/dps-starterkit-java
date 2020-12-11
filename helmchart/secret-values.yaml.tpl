nsSpecificVars:
  di-dev:
    - name: DB_NAME
      value: "starter"
    - name: DB_PORT
      value: "5432"
    - name:
      value: "localhost"
    - name: DB_USERNAME
      value: {{ twdps/di/svc/aurora/psql/starter/username }}
    - name: DB_PASSWORD
      value: "{{ twdps/di/svc/aurora/psql/starter/password }}"
    - name: ROOT_DB_USERNAME
      value: "{{ twdps/di/svc/aurora/psql/username }}"
    - name: ROOT_DB_PASSWORD
      value: "{{ twdps/di/svc/aurora/psql/password }}"


      - name: SECRET_USERNAME
        valueFrom:
          secretKeyRef:
            name: mysecret
            key: username
      - name: SECRET_PASSWORD
        valueFrom:
          secretKeyRef:
            name: mysecret
            key: password