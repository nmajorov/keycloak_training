# Keycloak (RedHat SSO) self-training


## Preparation

### set up vagrant

Open  port 80 from host and start host httpd server :


        sudo firewallctl zone FedoraWorkstation  add  port 80/tcp
        sudo systemctl start httpd

allow http server read user  content:

    sudo setsebool -P httpd_read_user_content 1


start fedora vagrant with command:

        vagrant up

to know vagrant ssh configuration:

        vagrant ssh-config


be also sure u have it in config:

        config.ssh.forward_agent = "true"

forward port from guest with ssh


        vagrant ssh -- -L8080:127.0.0.1:8080


### ansible keycloak standalone

run playbook with command:

      ansible-playbook keycloack-ansible/site.yml

change variables in file:

      keycloack-ansible/roles/common/vars/main.yml

especially dowload url.

## Training 


User login to specific realm user url:


            http://geneva:8080/auth/realms/<the realm name>/account/

Example:

[http://geneva:8080/auth/realms/nmRealm/account/](http://geneva:8080/auth/realms/nmRealm/account/)


Create quickstart realm:

                #login first
                /usr/share/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080/auth \
                --realm master --user admin --password Basel2016

                /usr/share/keycloak/bin/kcadm.sh create realms -f /usr/share/jboss/quickstart-realm.json

Register client to quickstart realm:

                /usr/share/keycloak/bin/kcadm.sh create clients -r quickstart \
                -f /vagrant/keycloak-quickstarts/service-jee-jaxrs/config/client-import.json  


Workflow for admin client:

request:

                POST /auth/realms/master/protocol/openid-connect/token HTTP/1.1
                Content-Type: application/x-www-form-urlencoded
                Accept: application/json
                Content-Length: 73
                Host: 10.42.0.5:8080
                Connection: Keep-Alive
                User-Agent: Apache-HttpClient/4.5 (Java/1.8.0_131)
                Accept-Encoding: gzip,deflate

                grant_type=password&username=admin&password=Basel2016&client_id=admin-cli

response:

                HTTP/1.1 200 OK
                Connection: keep-alive
                X-Powered-By: Undertow/1
                Server: WildFly/10
                Content-Type: application/json
                Content-Length: 4735
                Date: Wed, 28 Jun 2017 14:45:03 GMT

                {"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiI2OTVlOGVjOS1hMjMyLTRjNWUtYTk1MS00OWFiZjUwMjM5NjIiLCJleHAiOjE0OTg2NjExNjMsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTAzLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiN2Q2YmE4ZTItNTE4Ny00OTM2LWIyNGQtMWE2OTRmYjIxODc3IiwiYWNyIjoiMSIsImNsaWVudF9zZXNzaW9uIjoiMzM5OTIyM2MtOWMxYi00MzMwLWFhMmItZGU3NDNkNzJhYzQ3IiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19LCJxdWlja3N0YXJ0LXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19fSwibmFtZSI6IiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.HYeZh3zMivKho6d7et-8OwYapTPlzEsaO9yuTUSemFlW6iMsVO40567JOPF44QmNmmrmUPN8ceeEPuVVPI0_yAFCeo2YHj5Is4q2L1l73OeklM7s8Er04ZYEq_H9Dli6fkRap4hcUt6YYfhNLgn10ZjRk6iMO07VzCVippTFuEaudDJ6sxA8iFADXzqR8iNFJ6RY0-PfU_q91rEII5idPg-Mg9pUbaMpHXr00FSmDZrFqaZpk5nAU1xz8nPaIrgVgoWiLpZYYtVxwNrZto2sVHeUf_0HiyV2_uKzD48kTjRUZh9RIvlGk2Lv8pyUxwpwP3flnLRYrcaJCgKTuzJ55g","expires_in":60,"refresh_expires_in":1800,"refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiJmMTgyYTI5Ny1lYWFiLTQ0M2UtYjRkOS02Nzc3OWY0YzQ2YjYiLCJleHAiOjE0OTg2NjI5MDMsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTAzLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImFkbWluLWNsaSIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdkNmJhOGUyLTUxODctNDkzNi1iMjRkLTFhNjk0ZmIyMTg3NyIsImNsaWVudF9zZXNzaW9uIjoiMzM5OTIyM2MtOWMxYi00MzMwLWFhMmItZGU3NDNkNzJhYzQ3IiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19LCJxdWlja3N0YXJ0LXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19fX0.PfoWGqv2kOG92Q2mQSS8xrCsU6kgYsxXZb-BTAD9BRQxgVydUzrckwZlLzgqHGzxzfeLf9cR1qTvFNr4zRLp4ea3zsF-puL9AfZlgSOz542TFa98FSPLB1Uvg0UTOLVGjK0ANB3kcYAQK_F4SK518fVhXaw1hz-_-NUQ8b82L6rS9zsb5s55zQWf_i41Y_bBCFf2XeMnbduMaTSsX8JRi9NooZq8kOT6FPo5A_MGg1zZrqlqf-V9xV6O50WKgHHstwapisqEzubx3ptwJ3Y87_YQUEu94hKHb3hYQHhGF9ocF_WMiNtB14hg93gDAAsTLAebz1WaiuDpL7KS7VFA5Q","token_type":"bearer","id_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiI1YjU1Mzg5MC0xMDgwLTQyN2EtYjEyNy1mZDQyYTc1ZTgxNjEiLCJleHAiOjE0OTg2NjExNjMsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTAzLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiSUQiLCJhenAiOiJhZG1pbi1jbGkiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI3ZDZiYThlMi01MTg3LTQ5MzYtYjI0ZC0xYTY5NGZiMjE4NzciLCJhY3IiOiIxIiwibmFtZSI6IiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.CylV7ev1P1n-01f1YynOuhtklbTiYQvnd0acOjFCyMurgNe07poUkCp3PDYA0898wcgDKZTe4gYCk_9g9UHJdJxQ6QbW7DbVC70WC0vdgLxHVWI4yssZUjHQ2oSNoQIfZ5rDABY-1yEyzKwMmjLlQOattQkjbzZ3D--TbOg325vmpidMrOGukaKy5WnXFg4m1sdU91Ak3RmcfApDQnnTDzeY2O7iY4W3H3hn3miLI6NryuJyXb34miOloO-Oh-Qjq7izxl1XHm0Ka1mERMJS-XzrPHKQ6LruAxnz7EAKP31CS9_e7xknQmrrkyEMJnlo6vA4AI_eF35nyhMgOCrbmQ","not-before-policy":0,"session_state":"7d6ba8e2-5187-4936-b24d-1a694fb21877"}



refresh token request:

        POST /auth/realms/master/protocol/openid-connect/token HTTP/1.1
        Content-Type: application/x-www-form-urlencoded
        Accept: application/json
        Content-Length: 1825
        Host: 10.42.0.5:8080
        Connection: Keep-Alive
        User-Agent: Apache-HttpClient/4.5 (Java/1.8.0_131)
        Accept-Encoding: gzip,deflate

        grant_type=refresh_token&refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiJmMTgyYTI5Ny1lYWFiLTQ0M2UtYjRkOS02Nzc3OWY0YzQ2YjYiLCJleHAiOjE0OTg2NjI5MDMsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTAzLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImFkbWluLWNsaSIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdkNmJhOGUyLTUxODctNDkzNi1iMjRkLTFhNjk0ZmIyMTg3NyIsImNsaWVudF9zZXNzaW9uIjoiMzM5OTIyM2MtOWMxYi00MzMwLWFhMmItZGU3NDNkNzJhYzQ3IiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19LCJxdWlja3N0YXJ0LXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19fX0.PfoWGqv2kOG92Q2mQSS8xrCsU6kgYsxXZb-BTAD9BRQxgVydUzrckwZlLzgqHGzxzfeLf9cR1qTvFNr4zRLp4ea3zsF-puL9AfZlgSOz542TFa98FSPLB1Uvg0UTOLVGjK0ANB3kcYAQK_F4SK518fVhXaw1hz-_-NUQ8b82L6rS9zsb5s55zQWf_i41Y_bBCFf2XeMnbduMaTSsX8JRi9NooZq8kOT6FPo5A_MGg1zZrqlqf-V9xV6O50WKgHHstwapisqEzubx3ptwJ3Y87_YQUEu94hKHb3hYQHhGF9ocF_WMiNtB14hg93gDAAsTLAebz1WaiuDpL7KS7VFA5Q&client_id=admin-cliGET /auth/admin/realms HTTP/1.1
        Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiJmMDI4MDBiZi03NDVkLTQyZGYtYjEzNC1mMjk1MjhlNjgzZGYiLCJleHAiOjE0OTg2NjEyMjEsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTYxLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiN2Q2YmE4ZTItNTE4Ny00OTM2LWIyNGQtMWE2OTRmYjIxODc3IiwiYWNyIjoiMSIsImNsaWVudF9zZXNzaW9uIjoiMzM5OTIyM2MtOWMxYi00MzMwLWFhMmItZGU3NDNkNzJhYzQ3IiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19LCJxdWlja3N0YXJ0LXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19fSwibmFtZSI6IiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.U449NyAglzp9JiTNWwg7mi9AKwtD4tWo7t_lKqYv2zBHIcIA34ZFBOiy0wSsl9tRMlSeYvjh3rVTczjWoxTskoNLuYOwfin8_4fsZIfX1XPYAMs8R_xQZiLZlvctvSWdWdd2cGX19pqtgLjESmtLJLvFMCQGsNIbLWCV5nhQG00VoSy-ah8lvuy1XOZ78x57H1nwKPeCzREdsw0G99og27A4T_ONXVSd6kmNxwRA2MylW0m-BV9fV-OGohJnf3zieV2cub53RrhiTqjlx8b9tr6_mCiTQMLDG9TB3PIQOPXrYP5cKLOgkdLvco5mikwPZNFV1zDoP6J0WZXAup7Meg
        Accept: application/json
        Host: 10.42.0.1:8080
        Connection: Keep-Alive
        User-Agent: Apache-HttpClient/4.5 (Java/1.8.0_131)
        Accept-Encoding: gzip,deflate


response:

        HTTP/1.1 200 OK
        Connection: keep-alive
        X-Powered-By: Undertow/1
        Server: WildFly/10
        Content-Type: application/json
        Content-Length: 4735
        Date: Wed, 28 Jun 2017 14:46:01 GMT

        {"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiJmMDI4MDBiZi03NDVkLTQyZGYtYjEzNC1mMjk1MjhlNjgzZGYiLCJleHAiOjE0OTg2NjEyMjEsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTYxLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiN2Q2YmE4ZTItNTE4Ny00OTM2LWIyNGQtMWE2OTRmYjIxODc3IiwiYWNyIjoiMSIsImNsaWVudF9zZXNzaW9uIjoiMzM5OTIyM2MtOWMxYi00MzMwLWFhMmItZGU3NDNkNzJhYzQ3IiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19LCJxdWlja3N0YXJ0LXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19fSwibmFtZSI6IiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.U449NyAglzp9JiTNWwg7mi9AKwtD4tWo7t_lKqYv2zBHIcIA34ZFBOiy0wSsl9tRMlSeYvjh3rVTczjWoxTskoNLuYOwfin8_4fsZIfX1XPYAMs8R_xQZiLZlvctvSWdWdd2cGX19pqtgLjESmtLJLvFMCQGsNIbLWCV5nhQG00VoSy-ah8lvuy1XOZ78x57H1nwKPeCzREdsw0G99og27A4T_ONXVSd6kmNxwRA2MylW0m-BV9fV-OGohJnf3zieV2cub53RrhiTqjlx8b9tr6_mCiTQMLDG9TB3PIQOPXrYP5cKLOgkdLvco5mikwPZNFV1zDoP6J0WZXAup7Meg","expires_in":60,"refresh_expires_in":1800,"refresh_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiJjMzA4YzMzMy00MGJjLTRiM2ItYjNlNS1iZTdhMjkzOGM2OGUiLCJleHAiOjE0OTg2NjI5NjEsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTYxLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImFkbWluLWNsaSIsImF1dGhfdGltZSI6MCwic2Vzc2lvbl9zdGF0ZSI6IjdkNmJhOGUyLTUxODctNDkzNi1iMjRkLTFhNjk0ZmIyMTg3NyIsImNsaWVudF9zZXNzaW9uIjoiMzM5OTIyM2MtOWMxYi00MzMwLWFhMmItZGU3NDNkNzJhYzQ3IiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19LCJxdWlja3N0YXJ0LXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19fX0.PBNwWV19sZwxDxTBmOS0UFV_vwaEOZU5kwdcpLD0z8U0LtWfQ5S5dGRJv0OQsJUemrRnVaAZpH3f3cySEVC1w2KJRtMv9KV0Q6By6U9br_qtLWx_7_Rw7w2QpaiATpNH1XdhsBJFpVUxjL6NdWG5aBmHnO0NmWFRnGL4fAQvkj3w9J-I6yI_M4CK2LBDcRLyaUgcqxV0jaOB6d5Ie2Yn7LjOOadfPQesGlcZhTR-q3I8g2SCfr23SCotO-KKxdvBcLPKmenToHdgu3u7umjGO9B6tl_2jK_D0T8jXmtH7cAp7L6Zt_P3ZRrhs7eydIHjuJV5ctK9v3fbc84ZnKs0vw","token_type":"bearer","id_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiI0YzBhZGNlMS0yZTZkLTQ0YTYtYTc5OC1hNGJjMWEyZjJlNjQiLCJleHAiOjE0OTg2NjEyMjEsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTYxLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiSUQiLCJhenAiOiJhZG1pbi1jbGkiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI3ZDZiYThlMi01MTg3LTQ5MzYtYjI0ZC0xYTY5NGZiMjE4NzciLCJhY3IiOiIxIiwibmFtZSI6IiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.LOIg7sidembmNMC0E0iS63ll-SHc-5QZnqvHVc1_g9KfODcykLS0-z_-YqND9D637NmLhwrDKLdVtONeTGQV45atv2TAwL_8XJqyyDc8crGk4hpzwQ6vWzxfz1Tj6waBdtggajT_8J4uSzRMOWW6DpAh4cnhJQJRWw499VydFOnl-_nWShNiaFB-met83YUw39PbfeoM7kxf62RtkB_rsgTHSXgvrSU8-D6KoJrwmDbssccyiZKagF-ladMyRs90T07KlYHsny6g7y1obAMwbmmuug-dQlO7LY6VD9yZX-XjDhqI5ZFOhOFdtD3o1CLaqyVkEo1-tylo2Z_3rTBdKA","not-before-policy":0,"session_state":"7d6ba8e2-5187-4936-b24d-1a694fb21877"}HTTP/1.1 401 Unauthorized
        Connection: keep-alive
        X-Powered-By: Undertow/1
        Server: WildFly/10
        Content-Type: text/html
        Content-Length: 6
        Date: Wed, 28 Jun 2017 14:46:01 GMT

        Bearer


Get realms:

        GET /auth/admin/realms HTTP/1.1
        Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIybXFlbm9pUTlTb0Ztd2ctQWo4TmpadHRZS1ZDTFN4eWZmRlhIZXFxdmo4In0.eyJqdGkiOiJmMDI4MDBiZi03NDVkLTQyZGYtYjEzNC1mMjk1MjhlNjgzZGYiLCJleHAiOjE0OTg2NjEyMjEsIm5iZiI6MCwiaWF0IjoxNDk4NjYxMTYxLCJpc3MiOiJodHRwOi8vMTAuNDIuMC41OjgwODAvYXV0aC9yZWFsbXMvbWFzdGVyIiwiYXVkIjoiYWRtaW4tY2xpIiwic3ViIjoiZTBmYzFhNGItNDIyNC00ZWZkLTliYzEtYmRhNTljZGYxMGI5IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYWRtaW4tY2xpIiwiYXV0aF90aW1lIjowLCJzZXNzaW9uX3N0YXRlIjoiN2Q2YmE4ZTItNTE4Ny00OTM2LWIyNGQtMWE2OTRmYjIxODc3IiwiYWNyIjoiMSIsImNsaWVudF9zZXNzaW9uIjoiMzM5OTIyM2MtOWMxYi00MzMwLWFhMmItZGU3NDNkNzJhYzQ3IiwiYWxsb3dlZC1vcmlnaW5zIjpbXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImNyZWF0ZS1yZWFsbSIsImFkbWluIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsibWFzdGVyLXJlYWxtIjp7InJvbGVzIjpbInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwidmlldy1yZWFsbSIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19LCJxdWlja3N0YXJ0LXJlYWxtIjp7InJvbGVzIjpbInZpZXctcmVhbG0iLCJ2aWV3LWlkZW50aXR5LXByb3ZpZGVycyIsIm1hbmFnZS1pZGVudGl0eS1wcm92aWRlcnMiLCJpbXBlcnNvbmF0aW9uIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19fSwibmFtZSI6IiIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.U449NyAglzp9JiTNWwg7mi9AKwtD4tWo7t_lKqYv2zBHIcIA34ZFBOiy0wSsl9tRMlSeYvjh3rVTczjWoxTskoNLuYOwfin8_4fsZIfX1XPYAMs8R_xQZiLZlvctvSWdWdd2cGX19pqtgLjESmtLJLvFMCQGsNIbLWCV5nhQG00VoSy-ah8lvuy1XOZ78x57H1nwKPeCzREdsw0G99og27A4T_ONXVSd6kmNxwRA2MylW0m-BV9fV-OGohJnf3zieV2cub53RrhiTqjlx8b9tr6_mCiTQMLDG9TB3PIQOPXrYP5cKLOgkdLvco5mikwPZNFV1zDoP6J0WZXAup7Meg
        Accept: application/json
        Host: 10.42.0.5:8080
        Connection: Keep-Alive
        User-Agent: Apache-HttpClient/4.5 (Java/1.8.0_131)
        Accept-Encoding: gzip,deflate


response:

        HTTP/1.1 200 OK
        Connection: keep-alive
        Cache-Control: no-cache
        X-Powered-By: Undertow/1
        Server: WildFly/10
        Content-Type: application/json
        Content-Length: 4438
        Date: Wed, 28 Jun 2017 14:46:07 GMT

        [{"id":"master","realm":"master","displayName":"Keycloak","displayNameHtml":"<div class=\"kc-logo-text\"><span>Keycloak</span></div>","notBefore":0,"revokeRefreshToken":false,"accessTokenLifespan":60,"accessTokenLifespanForImplicitFlow":900,"ssoSessionIdleTimeout":1800,"ssoSessionMaxLifespan":36000,"offlineSessionIdleTimeout":2592000,"accessCodeLifespan":60,"accessCodeLifespanUserAction":300,"accessCodeLifespanLogin":1800,"enabled":true,"sslRequired":"external","registrationAllowed":false,"registrationEmailAsUsername":false,"rememberMe":false,"verifyEmail":false,"loginWithEmailAllowed":true,"duplicateEmailsAllowed":false,"resetPasswordAllowed":false,"editUsernameAllowed":false,"bruteForceProtected":false,"maxFailureWaitSeconds":900,"minimumQuickLoginWaitSeconds":60,"waitIncrementSeconds":60,"quickLoginCheckMilliSeconds":1000,"maxDeltaTimeSeconds":43200,"failureFactor":30,"defaultRoles":["offline_access","uma_authorization"],"requiredCredentials":["password"],"passwordPolicy":"hashIterations(20000)","otpPolicyType":"totp","otpPolicyAlgorithm":"HmacSHA1","otpPolicyInitialCounter":0,"otpPolicyDigits":6,"otpPolicyLookAheadWindow":1,"otpPolicyPeriod":30,"browserSecurityHeaders":{"xContentTypeOptions":"nosniff","xRobotsTag":"none","xFrameOptions":"SAMEORIGIN","xXSSProtection":"1; mode=block","contentSecurityPolicy":"frame-src 'self'"},"smtpServer":{},"eventsEnabled":false,"eventsListeners":["jboss-logging"],"enabledEventTypes":[],"adminEventsEnabled":false,"adminEventsDetailsEnabled":false,"internationalizationEnabled":false,"supportedLocales":[],"browserFlow":"browser","registrationFlow":"registration","directGrantFlow":"direct grant","resetCredentialsFlow":"reset credentials","clientAuthenticationFlow":"clients","attributes":{"_browser_header.xXSSProtection":"1; mode=block","_browser_header.xFrameOptions":"SAMEORIGIN","quickLoginCheckMilliSeconds":"1000","displayName":"Keycloak","_browser_header.xRobotsTag":"none","maxFailureWaitSeconds":"900","minimumQuickLoginWaitSeconds":"60","displayNameHtml":"<div class=\"kc-logo-text\"><span>Keycloak</span></div>","failureFactor":"30","maxDeltaTimeSeconds":"43200","_browser_header.xContentTypeOptions":"nosniff","bruteForceProtected":"false","_browser_header.contentSecurityPolicy":"frame-src 'self'","waitIncrementSeconds":"60"}},{"id":"a0d8dc8c-747e-4e3a-914f-8d513345972d","realm":"quickstart","notBefore":0,"revokeRefreshToken":false,"accessTokenLifespan":60,"accessTokenLifespanForImplicitFlow":900,"ssoSessionIdleTimeout":600,"ssoSessionMaxLifespan":36000,"offlineSessionIdleTimeout":2592000,"accessCodeLifespan":60,"accessCodeLifespanUserAction":300,"accessCodeLifespanLogin":1800,"enabled":true,"sslRequired":"external","registrationAllowed":false,"registrationEmailAsUsername":false,"rememberMe":false,"verifyEmail":false,"loginWithEmailAllowed":true,"duplicateEmailsAllowed":false,"resetPasswordAllowed":false,"editUsernameAllowed":false,"bruteForceProtected":false,"maxFailureWaitSeconds":900,"minimumQuickLoginWaitSeconds":60,"waitIncrementSeconds":60,"quickLoginCheckMilliSeconds":1000,"maxDeltaTimeSeconds":43200,"failureFactor":30,"defaultRoles":["offline_access","uma_authorization"],"requiredCredentials":["password"],"passwordPolicy":"hashIterations(20000)","otpPolicyType":"totp","otpPolicyAlgorithm":"HmacSHA1","otpPolicyInitialCounter":0,"otpPolicyDigits":6,"otpPolicyLookAheadWindow":1,"otpPolicyPeriod":30,"browserSecurityHeaders":{"xContentTypeOptions":"nosniff","xRobotsTag":"none","xFrameOptions":"SAMEORIGIN","xXSSProtection":"1; mode=block","contentSecurityPolicy":"frame-src 'self'"},"smtpServer":{},"eventsEnabled":false,"eventsListeners":["jboss-logging"],"enabledEventTypes":[],"adminEventsEnabled":false,"adminEventsDetailsEnabled":false,"internationalizationEnabled":false,"supportedLocales":[],"browserFlow":"browser","registrationFlow":"registration","directGrantFlow":"direct grant","resetCredentialsFlow":"reset credentials","clientAuthenticationFlow":"clients","attributes":{"_browser_header.xXSSProtection":"1; mode=block","_browser_header.xFrameOptions":"SAMEORIGIN","failureFactor":"30","quickLoginCheckMilliSeconds":"1000","maxDeltaTimeSeconds":"43200","_browser_header.xContentTypeOptions":"nosniff","_browser_header.xRobotsTag":"none","bruteForceProtected":"false","maxFailureWaitSeconds":"900","_browser_header.contentSecurityPolicy":"frame-src 'self'","minimumQuickLoginWaitSeconds":"60","waitIncrementSeconds":"60"}}]


webservice call example:



                curl 'http://localhost:8081/service/admin' -H 'Accept-Encoding: gzip, deflate, br' -H 'Accept-Language: en' -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJGSjg2R2NGM2pUYk5MT2NvNE52WmtVQ0lVbWZZQ3FvcXRPUWVNZmJoTmxFIn0.eyJqdGkiOiJkZTA1N2ExNi1lNDAzLTQ4ZDUtYTJmMS0zNTNiZGVlYzZhNTAiLCJleHAiOjE0OTkxNjI0NDIsIm5iZiI6MCwiaWF0IjoxNDk5MTYyMzgyLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvcXVpY2tzdGFydCIsImF1ZCI6ImFwcC1odG1sNSIsInN1YiI6ImFkZGMyNTQ0LTRlOTctNDc1OC1iOTU4LTVmZjFjZjVlYmFjMiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFwcC1odG1sNSIsIm5vbmNlIjoiMzA1NzRlYjktM2VlMC00ZTBlLTlhNGEtNTUwMzEyNDU4Mzk4IiwiYXV0aF90aW1lIjoxNDk5MTYyMjI5LCJzZXNzaW9uX3N0YXRlIjoiN2M4NzAxM2UtNDBmNy00MDE0LTliNzItNjdjNTRhN2FlMmQ3IiwiYWNyIjoiMCIsImNsaWVudF9zZXNzaW9uIjoiZGRlNTc2M2ItMTJhOS00ZDU4LWEwNmMtMjc1NmYwMjQ2YWYxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MSJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsicmVhbG0tbWFuYWdlbWVudCI6eyJyb2xlcyI6WyJ2aWV3LXJlYWxtIiwidmlldy1pZGVudGl0eS1wcm92aWRlcnMiLCJtYW5hZ2UtaWRlbnRpdHktcHJvdmlkZXJzIiwiaW1wZXJzb25hdGlvbiIsInJlYWxtLWFkbWluIiwiY3JlYXRlLWNsaWVudCIsIm1hbmFnZS11c2VycyIsInZpZXctYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1ldmVudHMiLCJtYW5hZ2UtcmVhbG0iLCJ2aWV3LWV2ZW50cyIsInZpZXctdXNlcnMiLCJ2aWV3LWNsaWVudHMiLCJtYW5hZ2UtYXV0aG9yaXphdGlvbiIsIm1hbmFnZS1jbGllbnRzIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Il19fSwibmFtZSI6IkFkbWluIFRlc3QiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ0ZXN0LWFkbWluIiwiZ2l2ZW5fbmFtZSI6IkFkbWluIiwiZmFtaWx5X25hbWUiOiJUZXN0IiwiZW1haWwiOiJ0ZXN0QGFkbWluLm9yZyJ9.QaA7PC7_WEbR08eeNpSbWloQ5uRKzd-OGsmKarG9lKJFoyY0GYJf2uxyJD34AC5vZ5qKkFNg9D1gnLvpvpDs-3UHpyol8OFWS8_oZr2DZbwAen8WPb030pjYHmC9nXKSnjOjgX4pN5ZxgvWrm0QV5Yyc9LGG2v5zoO4ti083ORg' -H 'Accept: */*' -H 'Referer: http://localhost:8081/app-html5/' -H 'User-Agent: Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.109 Safari/537.36' -H 'Connection: keep-alive' --compressed


## Configure app-html5 in keycloak

Make changes to access the web-service call:

![./images/keycloak-client001.jpeg](./images/keycloak-client001.jpeg)

after this it is possible make a restfull call to ask a token.

Changes to js client to avoid annoeyd iframe checks:

         keycloak.init({ onLoad: 'check-sso', checkLoginIframe: false })



