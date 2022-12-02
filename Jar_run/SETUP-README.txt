Portfolio application is the class that has the main method.
java -jar PortfolioApplication.jar should run the program.
The controller prompts the model and the view to perform the operations.
The view asks for name of the user.
All the ticker data that can be inputted are in the Tickers.txt file. Some of the valid
tickers are GOOG, AAPL, TWST, BBD, BBF, BBH, YEAR, BBW, BBY, CEAD, ZHDG.

*************************************THIRD PARTY LIBRARY:***********************************************  

  1.  A third party library named Jfreechart has been used to plot the grpah in the GUI.
  2.  It is a free to use library which can be used by the general public.
  3.  LINK TO HOMEPAGE OF LIBRARY: https://www.jfree.org/jfreechart/
  4.  LINK TO THE TERMS OF USAGE : https://www.gnu.org/licenses/lgpl-3.0.html
  5.  To install the library please download the jfreechart v1.0.1 from the internet
  6.  Unzip the package.
  7.  Open IntelliJ -> File -> Project Structure -> Libraries -> Click on '+' -> Java -> Add both jcommon-1.0.0.jar and jfreechart-1.0.1.jar (present in the lib folder of the unzipped             package) -> Choose the module to which it has to be added -> Click on Apply -> Click on OK. Now the library is ready to use.



************************************* TO RUN THE APPLICATION: *********************************************

  1.  java -jar HWK_4.jar
  2.  Make sure that the Resources folder along with the jar file should be present together in
      any folder.
  3.  The Resources folder contains Tickers.txt and Users folder.
  4.  Upon running the portfolio application, you will be prompted to choose between the Text line interface and the Graphical User Interface. Enter 1 and press enter for the Text Line            
	interface or Enter 2 and press enter to choose the Graphical User Interface.
  5.  If you have chosen the Text Line Interface please follow the steps listed under the TEXT LINE INTERFACE. If you have chose the Graphical User Interface please follow the steps listed        
	under the GRAPHICAL USER INTERFACE. 

************************************* TEXT LINE INTERFACE: ***********************************************    	

  1.  To create a new portfolio, enter a new user name consisting of only alphanumeric characters and _.
  2.  A new user is created for you.
  3.  To create a new portfolio, enter "C".
  4.  Enter one ticker symbol, like GOOG, AAPL, TWST, BBD, BBF, BBH.
  5.  Enter the number of shares.
  6.  Shares should be a whole positive number.
  7.  Enter any date in YYYY-MM-DD,stock price (for eg: 2022-10-15,55.5).
  8.  Type "yes" to add 2nd stock. Do the previous 3 steps again.
  9.  Type "yes" to add 3rd stock. Do the previous 3 steps again.
  10. Type anything other than "yes" to stop adding stock.
  11. Portfolio was created.
  12. Type anything other than "yes" or "getvalue" to finish.
  13. Do steps 4-11 to create a second portfolio with 2 stocks.
  14. Do step 15.
  15. Portfolio was created.
  16. Type "getvalue" to find the total value of portfolio.
  17. Enter date in YYYY-MM-DD format or enter "current" for current date.
  18. Total value of portfolio is displayed.

************************************* GRAPHICAL USER INTERFACE: **********************************************

  1.  To use the graphical user interface, the user has to click on the create user button first or choose a pre-existing username to proceed.
  2.  Enter the unique username and the file path to which the portfolios are to be stored or retrived from has to be entered and click on the Create User button.
	 The username will be created. Click on Home to return to the main menu.
  3.  Now to go into the specific user mode, click on the Show all users/ Choose a user button.
  4.  List of the users will be displayed. Enter the user name and click on the Select User button to select the user. Click on Home to return to the main menu.
  5.  To create a portfolio, click on the Create a new Portfolio / Load button and enter the desired file name. Once entered, please click on the Create / Read button to create the portfolio.
	 An empty portfolio will be created. All the operations will be performed on this portfolio untill another portfolio is loaded. Click on Home to return to the main menu.
  6.  Once the empty portfolio is created, click on the Buy / Sell Stocks button to buy stocks and add it to the empty portfolio which has been recently created.
  7.  Click on the Buy Stocks button to enter the buy screen. Enter the stock symbol. Enter the buy date, Volume, Commission Fee in the following format:             
		(STOCK_SYMBOL,VOLUME,COMMISSION_FEE). This has to include the braces as well. Buy date has to be in YYYY-MM-DD format. FOR EG. (2022-10-28,20,10)
  8.  Click on Buy stocks once the requirements are entered. The stocks will be bought and will be added to the portfolio created. Click on Home to return to the main menu.
  9.  To sell the stocks, click on the Buy / Sell Stocks button to sell stocks and update portfolio which has been recently created.
  10. Click on the Sell Stocks button to enter the sell screen. Enter the stock symbol. Enter the sell date, Volume, Commission Fee. Sell date has to be in YYYY-MM-DD format. Click on Home         
	to return to the main menu.
  11. To get all the portfolios that have bee created, click on the Show All Portfolio button to view them. Click on Home to return to the main menu.
  12. To query the cost basis of the portfolio on the current date, click on the Check Cost Basis button. click on the check cost basis button and click calculate to get the cost basis on         
	the current date. Click on Home to return to the main menu.
  13. To query the cost basis of the portfolio on a given date, click on the Check Cost Basis button. click on the check cost basis by date button. Enter the date on which the cost          
	basis of the portfolio has to be calculated and click calculate to get the cost basis on a given date.
  14. To query the total value of the portfolio on the current date, click on the Check Total Value button. click on the check total value button and click calculate to get the total value        
	of the portfolio on the current date. Click on Home to return to the main menu.
  15. To query the total value of the portfolio on a given date, click on the Check Total Value button. click on the check total value by date button. Enter the date on which the                  
	total value of the portfolio has to be calculated and click calculate to get the total value on a given date. Click on Home to return to the main menu.
  16. To invest using the Dollar Cost Averaging, click on the Dollar Cost Averaging. Enter the number of days of investment. Enter the start date. Enter the end date. Enter the stock symbol       
	and proportion in the following format : (STOCK_SYMBOL, PROPORTION). Enter the commission fee. Enter the amount to be invested. Click on Invest button to invest using this strategy.         
	If you would like to perform DCA only once on the start date, set the number of days to 0. You may leave the end date blank for endless strategy. You may add a past start date but not       
	more than 1 full time frame in the past. Your proportions/weights must be entered as: (stock_ticker1, weight1);(ticker2, weight2),. The stock weights entered must be between [0,1] and
      	must sum in total to 1.0. Any tickers missing from the mapping input string will be assigned a proportional weight of 0. Click on Home to return to the main menu.
  17. To get the portfolio performance graph, click on the portfolio performance button. Enter the start date. Enter the end date. Click on plot to get the performance graph. Close the       
	graph by clicking on the cross button of the graph popup.
  18. To Load a portfolio, click on the Create a Portfolio / Load button. Enter the full path of the file to be loaded into the program and click on the Create / Read button to finish       
	loading the portfolio. Click on Home to return to the main menu.
  19. To Save a portfolio, click on the Save portfolio to File button. If you want to save it to the path associated to the username, click on the save button. Otherwise please enter the       
	path of the folder to which you want the portfolio to be saved, and click Save to finish saving the portfolio. Click on Home to return to the main menu.
  20. To quit the program, click on the quit button to exit the program.

FOR CREATION OF A NEW PORTFOLIO THROUGH Dollar Cost Averaging:
	1. First the user must create a new empty portfolio through the process described. Then the user must navigate to the Dollar Cost Averaging tab.
		To add new stocks through DCA the user must then:
	2. Specify tickers and weights in the provided input box for all stocks they want added
	3. Give a start date in the past or present.
	4. They must set the timespan/# of days = 0. This means that DCA will be performed only once on the specified start date.
	5. All stocks will be added to the portfolio through DCA but no future DCA strategy will be set (The end date can be blank or a date string --> it will not be used if the time span = 0)
	6. After completing the first DCA buy, the user can now set a strategy to start any date and end any date/continue forever. When setting a strategy over time (that buys in the future/more than once)
		 the user must specify only stocks that are present in the portfolio already.
	To summarize:
	 	If you would like to add new stocks through DCA add them once either through add stock or DCA by setting days = 0, then you can set strategy for those stocks.




