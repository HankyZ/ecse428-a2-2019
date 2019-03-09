Feature: Send an email with an image attachment using Gmail

  Scenario: Send the email after attaching an image file and writing the recipient and the subject
    Given I am on a Gmail page
    And I am logged in
    And an empty "New Message" window is opened
    When I write the email of the recipient
    And I write the subject
    And I attach an image file
    And I press "Send"
    Then the email should be sent
    And I should see it in the sent mailbox

  Scenario: Send the email after attaching an image file and writing the recipient but not the subject
    Given I am logged in
    And an empty "New Message" window is opened
    When I write the email of the recipient
    And I attach an image file
    And I press "Send"
    Then I should be asked if I want to send the email without a subject
    When I press "OK"
    Then the email should be sent
    And I should see it in the sent mailbox

  Scenario: Send the email after attaching an image file with size less than 25MB without writing the recipient
    Given I am logged in
    And an empty "New Message" window is opened
    When I attach an image file
    And I press "Send"
    Then my email is not sent
    And I should see an error message saying that I did not input a recipient