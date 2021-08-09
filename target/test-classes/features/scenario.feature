#Author: joana.pedroso15@gmail.com
Feature: Tests cases where user selects different kinds of products, adds to cart and purchases it

  @happy-path @scenario-01 
  Scenario: Selects two type of laptops, adds to cart, deletes one, finishes purchase
    Given the user acesses the homepage of the marketplace
    When user selects a product category "laptops"
    And selects the product "Sony vaio i5"
    Then adds the product to cart, accepts the pop-up confirmation
    And user goes back to the main page of the marketplace
    When user selects a product category "laptops"
    And selects the product "Dell i7 8gb"
    Then adds the product to cart, accepts the pop-up confirmation
    When user navigates to the cart page
    And deletes a product "Dell i7 8gb"
    And clicks on placing the order
    And fills the field "name" with the value "Joana"
    And fills the field "country" with the value "Portugal"
    And fills the field "city" with the value "Lisbon"
    And fills the field "credit card" with the value "444499191919"
    And fills the field "month" with the value "August"
    And fills the field "year" with the value "2021"
    And user finishes the purchase 
    Then user verifies the information of the purchase 
    And user closes the confirmation box
    
