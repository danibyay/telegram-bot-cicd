data "azurerm_resource_group" "rg" {
  name     = "telegram-group"
}

resource "azurerm_container_group" "aci" {
  name                = "danibcontainergroup"
  resource_group_name = data.azurerm_resource_group.rg.name
  location            = "canadacentral"
  ip_address_type     = "public"

  os_type             = "Linux"

  container {
    name   = "telegram-bot"
    image  = "danibish/my_first_bot:1.0.0"
    cpu    = "0.5"
    memory = "1.0"
    secure_environment_variables = var.token

    ports = [
      {
        port     = 80
        protocol = "TCP"
      },
      {
        port     = 443
        protocol = "TCP"
      }
    ]
  }

}

variable "token" {
  type        = string
  description = "api token for the app"
}