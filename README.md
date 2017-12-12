# multi-tenancy

## three modes

* Dicriminator-Column: there is a tenant-Column in every table

* Schema-Separation: all tenants use the same database, but tenant has their own schema

* Datasource-Separation: there is a datasource for every tenant that connect to different databases

## user interface

* domain/sub-domain
* path based
* param based
* cookie based
* user based

https://stackoverflow.com/questions/4987201/why-use-subdomains-to-designate-tenants-in-a-multi-tenant-web-application