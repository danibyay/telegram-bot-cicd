data "azurerm_resource_group" "rg" {
  name     = "telegram-group"
}

resource "azurerm_container_group" "aci" {
  name                = "danibcontainergroup"
  resource_group_name = data.azurerm_resource_group.rg.name
  location            = "canadacentral"
  ip_address_type     = "Public"

  os_type             = "Linux"

  container {
    name   = "telegram-bot"
    image  = "danibish/my_first_bot:${var.tag}"
    cpu    = "0.5"
    memory = "1.0"

    secure_environment_variables  = {
        TOKEN = var.token
    } 

    ports {
        port     = 80
        protocol = "TCP"
    }
    ports {
        port     = 443
        protocol = "TCP"
    }
    
  }

}

variable "token" {
  type        = string
  description = "api token for the app"
}

variable "tag" {
  type        = string
  description = "tag for the docker image to use"
  default     = "1.0.0"
}
