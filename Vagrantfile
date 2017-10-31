# vagrant Fedora24 box

Vagrant.configure(2) do |config|

  
  config.vm.box_url = "http://fedora.uib.no/fedora/linux/releases/24/CloudImages/x86_64/images/Fedora-Cloud-Base-Vagrant-24-1.2.x86_64.vagrant-virtualbox.box"
 
   config.vm.box = "f23-cloud-virtbox"
   config.ssh.forward_agent = "true" 

   config.vm.define "keycloak-server" do |nconfig|
    
 # config.vm.network "forwarded_port", guest: 6543, host: 6543
 #config.vm.network "forwarded_port", guest: 8080,  host: 8181
 # config.vm.synced_folder ".", "/vagrant", type: "sshfs"

    nconfig.vm.provider :virtualbox do |domain|
      domain.cpus = 2
      #domain.graphics_type = "spice"
      # The unit tests use a lot of RAM.
      domain.memory = 4096
    end

  #set hostname
   nconfig.vm.hostname = "keycloak-server"
   nconfig.vm.network :private_network, ip: "10.42.0.5"
   nconfig.vm.provision "shell", inline: "sudo dnf install -y libselinux-python python2-dnf"

  end
  
  config.vm.define "wildfly-server" do |nconfig|

    nconfig.vm.provider :virtualbox do |domain|
      domain.cpus = 1
      #domain.graphics_type = "spice"
      # The unit tests use a lot of RAM.
      domain.memory = 4096
    end

  #set hostname
   nconfig.vm.hostname = "wildfly-server"
   nconfig.vm.network :private_network, ip: "10.42.0.6"
   nconfig.vm.provision "shell", inline: "sudo dnf install -y libselinux-python python2-dnf"

  end
  
end
