provider "azurerm" {
  features {}
}

module "network" {
  source              = "./modules/network"
  resource_group_name = "my-resource-group"
  location            = "East US"
  virtual_network_name = "my-virtual-network"
  subnet_name         = "my-subnet"
}

module "nsg" {
  source              = "./modules/nsg"
  resource_group_name = "my-resource-group"
  location            = "East US"
  nsg_name            = "my-nsg"
}

module "vm" {
  source              = "./modules/vm"
  resource_group_name = "my-resource-group"
  location            = "East US"
  vm_name             = "my-vm"
  subnet_id           = module.network.subnet_id
  nsg_id              = module.nsg.nsg_id
}
