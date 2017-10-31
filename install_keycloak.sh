#!/bin/sh
ansible-playbook   -e target_host=keycloak-server  keycloak-ansible/site.yml
