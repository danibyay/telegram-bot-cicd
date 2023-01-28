terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "3.28.0"
    }
  }
  backend "azurerm" {
      resource_group_name  = "mi-terraform-RG"
      storage_account_name = "miterraformsa"
      container_name       = "tfstate"
      key                  = "ms-golden-image.tfstate"
  }

}

provider "azurerm" {
  features {}
}