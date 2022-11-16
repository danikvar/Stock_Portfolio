Portfolio application is the class that has the main method.
java -jar PortfolioApplication.jar should run the program.
The controller prompts the model and the view to perform the operations.
The view asks for name of the user.
All the ticker data that can be inputted are in the Tickers.txt file. Some of the valid
tickers are GOOG, AAPL, TWST, BBD, BBF, BBH, YEAR, BBW, BBY, CEAD, ZHDG.

To run the application:

  1.  java -jar HWK_4.jar
  2.  Make sure that the Resources folder along with the jar file should be present together in
      any folder.
  3.  The Resources folder contains Tickers.txt and Users folder.
  4.  To create a new portfolio, enter a new user name consisting of only alphanumeric characters and _.
  5.  A new user is created for you.
  7.  To create a new portfolio, enter "C".
  8.  Enter one ticker symbol, like GOOG, AAPL, TWST, BBD, BBF, BBH.
  9.  Enter the number of shares.
  10. Shares should be a whole positive number.
  10. Enter any date in YYYY-MM-DD,stock price (for eg: 2022-10-15,55.5).
  11. Type "yes" to add 2nd stock. Do the previous 3 steps again.
  12. Type "yes" to add 3rd stock. Do the previous 3 steps again.
  13. Type anything other than "yes" to stop adding stock.
  14. Portfolio was created.
  15. Type anything other than "yes" or "getvalue" to finish.
  16. Do steps 4-11 to create a second portfolio with 2 stocks.
  17. Do step 15.
  18. Portfolio was created.
  19. Type "getvalue" to find the total value of portfolio.
  20. Enter date in YYYY-MM-DD format or enter "current" for current date.
  21. Total value of portfolio is displayed.
