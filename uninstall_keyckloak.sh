#!/bin/bash

ansible-playbook -e target_host=main -e full_version=3.1.0.Final  \
    -e version=3.1 keycloak-ansible/uninstall_keyckloak.yml


