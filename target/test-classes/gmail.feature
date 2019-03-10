Feature: Send an email with an image attachment using Gmail

  Scenario Outline: Send the email after attaching an image file and writing the recipient and the subject
    Given I am on a Gmail page
    And I am logged in
    And I have opened an empty New Message window
    When I write the recipient <recipient>
    And I write the subject
    And I attach an image file <file>
    And I press Send
    Then I should see a success message
    Examples:
      | recipient          | file            |
      | yuhuan01@gmail.com | res\\flower.gif |
      | yuhuan01@gmail.com | res\\fruit.bmp  |
      | yuhuan01@gmail.com | res\\loop.png   |
      | yuhuan01@gmail.com | res\\star.jpg   |
      | yuhuan02@gmail.com | res\\star.jpg   |

  Scenario: Send the email after attaching an image file and writing the recipient but not the subject
    Given I am on a Gmail page
    And I am logged in
    And I have opened an empty New Message window
    When I write the recipient
    And I attach an image file
    And I press Send
    Then I should see a confirmation dialog
    When I press OK
    Then I should see a success message

  Scenario: Send the email after attaching an image file without writing the recipient
    Given I am on a Gmail page
    And I am logged in
    And I have opened an empty New Message window
    When I attach an image file
    And I press Send
    Then I should see an error message
