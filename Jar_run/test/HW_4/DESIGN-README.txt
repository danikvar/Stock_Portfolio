DESIGN:

We have a interface called StockPortfolio that is implemented by the Portfolio class
used to create a new portfolio for the user.
It can also add stocks, view portfolio, determine total value, save and load the portfolio at any
time.

Stock1 interface is implemented by Stock class that holds all information of a particular stock
including its ticker, shares and price and date when the stock was bought.
It processes all the data, validates them and then passes it to be stored in the portfolio.

DataHelpers class helps us to display the existing users, display the existing portfolios,
makes sure the padding is right for the display, creates user and gets directory of the portfolio.

TextInterface is a interface that is implemented by TextGUI to display all information to the
user regarding what inputs they must enter and in what format. Since this is a TextBasedInterface,
it only has print statements.

Controller interface that is implemented by TextController has the model and view telling
them what to do and when to do. It gets inputs from the user, passes them to model, displays output
and passes to view.

