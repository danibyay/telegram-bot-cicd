terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "3.28.0"
    }
  }
  backend "azurerm" {
      resource_group_name  = "tfstate"
      storage_account_name = "tfstate23904"
      container_name       = "tfstatebot"
      key                  = "my_first_bot_test.tfstate"
  }

}

provider "azurerm" {
  features {}
  skip_provider_registration = true
}