terraform {
  required_version = ">=0.12"

  required_providers {
    azapi = {
      source  = "azure/azapi"
      version = "~>1.5"
    }
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~>2.0"

    }
    random = {
      source  = "hashicorp/random"
      version = "~>3.0"
    }
  }
}
provider "azurerm" {
  features {}
    subscription_id   = "488a119a-b2e2-49eb-abfa-98c93480fc34"
    tenant_id         = "ed236744-d848-41e9-a7cd-e801c29208fc"
    client_id         = "9c0da9e1-33dc-4044-bc8a-854f91be2c72"
    client_secret     = "eFV8Q~OIaTxwFYd8yqtkHZt9cp5Yk8rGoAEQfc2-"
}