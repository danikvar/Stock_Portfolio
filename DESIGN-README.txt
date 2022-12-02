DESIGN CHANGES :

We have added GUI application of of the view and an operations class to perform operations on GUI. The view now has 2 extra packages: buttons and buttonsandtexts. These 
control the GUI buttons and GUI text input.



JUSTIFICATION :

Instead of changing any of the previous implementations we added the DCA to the SmartPortfolio since it is still flexible.
We created a new view objects and new controller to further abstracty our views allowing the text view and GUI view to function independantly.
Our operations object allows us to hold all important model information in one succint place to make it easier to load/save.
We also created a GUIController that handles all control for the GUI model and view.


DESIGN:

We have a interface called StockPortfolio that is implemented by the Portfolio class
used to create a new portfolio for the user.
It can also add stocks, view portfolio, determine total value, save and load the portfolio at any
time.

We have a class called SmartPortfolio that holds all the implementations for the new flexible portfolio,
this is also under the StockPortfolio interface.

The new stock for the SmartPortfolio is implemented in the SmartStock class that is also under the Stock1
interface. It holds all information of a particular stock including its ticker, shares and price and date
when the stock was bought. It can sell stock, by stock, print a performance graph, and perform dollar cost
averaging, and cost basis.
 
Stock1 interface is implemented by Stock class that holds all information of a particular stock
including its ticker, shares and price and date when the stock was bought.
It processes all the data, validates them and then passes it to be stored in the portfolio.

SmartStock is an implementation of Stock1 that is used by the SmartPortfolio. It holds all information needed
like buy dates, sell dates, cost, and weights for DCA.

DataHelpers class helps us to display the existing users, display the existing portfolios,
makes sure the padding is right for the display, creates user and gets directory of the portfolio.
It gets data for use in performance grpahs and helps parse portfolio files.

TextInterface is a interface that is implemented by TextGUI to display all information to the
user regarding what inputs they must enter and in what format. Since this is a TextBasedInterface,
it only has print statements.

Controller interface that is implemented by TextController has the model and view telling
them what to do and when to do. It gets inputs from the user to determine whether they want to build a
flexible or inflexible portfolio.

All the controller operations for the flexible portfolio is implemented in the SmartController class
and all the controller operations for the flexible portfolio is implemented in the SimpleController class.
The common operations to be performed by both controllers are present in the AbstractController class.

For the GUI 3 main parts are used. Our GUI view objects stored in the buttons and buttonsandtexts packages
 in the view package; our GUIController which controls the GUI, and the OperationsImpl class which implements
 Operations. Our buttons package had a Buttons interface and implemenations for pages that do not involve text
 input, like the DisplayAllPorts button which just displays portfolios when clicked.

Our GUIController is one class and sets up all necessart methods for the GUI.
Our Operations object and the implementation OperationsImp control the model for the GUIController.
The OperationsImpl has 3 string variables and one smartPortoflio variable that are intiliazied to be empty.
The user string holds the current user, the currnPort string contains the name of the current portfolio and
the saveDir string holds the save directory for the current portfolio. As the user and portfolio are selected
each object gets assigned to a new value, which will be used throughout the users time in the application.


