const THE_CARDS = [
    1.1, 1.2, 1.3, 1.4,
    2.1, 2.2, 2.3, 2.4,
    3.1, 3.2, 3.3, 3.4,
    4.1, 4.2, 4.3, 4.4,
    5.1, 5.2, 5.3, 5.4,
    6.1, 6.2, 6.3, 6.4,
    7.1, 7.2, 7.3, 7.4,
    8.1, 8.2, 8.3, 8.4,
    9.1, 9.2, 9.3, 9.4,
    10.1, 10.2, 10.3, 10.4,
    11.1, 11.2, 11.3, 11.4,
    12.1, 12.2, 12.3, 12.4,
    13.1, 13.2, 13.3, 13.4
];
const THE_SEATS = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16];

function playGame() {
    var playersArray = [];
    var deck = shuffleArray(THE_CARDS);    
    for(var i=1, len=THE_SEATS.length; i<=len; i++) {
        var play = {
            id: i,
            cards: [],
            firstCard: 0,
            secondCard: 0,
            inBetweenCard: 0,
            didWin: false,
            didDrawCard: false,
            chance: 0,
            decision: "",
            winner: false, 
            turn: 0,
            remarks: ""
        };

        /***
         * ==========================
         * draw 2 cards
         */
        var firstCard = deck.pop();
        play.firstCard = firstCard;
        
        var secondCard = deck.pop();
        play.secondCard = secondCard;
                
        var cards = [parseInt(firstCard), parseInt(secondCard)].sort(function(a, b){return a - b});
        play.cards.push(cards[0]);
        play.cards.push(cards[1]);
        /***
         * ==========================
         */

        var inBetween = deck.pop();
        play.inBetweenCard = inBetween;
        cards.push(parseInt(inBetween));        
        play.cards.push(cards[2]);
        
        if ( cards[2]<cards[1] && 
            cards[2]>cards[0] ) {
            play.didWin = true;
        }

        /**
         * calculate chance of winning
         */
        if (cards[1]-cards[0]==0) {
            play.chance = 0;
        } else if (cards[1]-cards[0]==12) {
            play.chance = 100;
        } else {
            play.chance = Math.round( ( (cards[1]-cards[0]) / 12) * 100);
        }

        playersArray.push(play);
    }
    return playersArray;
}

function copyArray(array) {
    var arrayCopy = [];
    for(var i=0,len=array.length;i<len;i++) {
        arrayCopy[i] = array[i];
    }
    return arrayCopy;
}

function shuffleArray(array) {
    var arrayCopy = copyArray(array);
    for (var i = arrayCopy.length - 1; i > 0; i--) {
        var j = Math.floor(Math.random() * (i + 1));
        var temp = arrayCopy[i];
        arrayCopy[i] = arrayCopy[j];
        arrayCopy[j] = temp;
    }
    return arrayCopy;
}

function hexEncode(text) {
    var hex, i;

    var result = "";
    for (i=0; i<text.length; i++) {
        hex = text.charCodeAt(i).toString(16);
        result += ("000"+hex).slice(-4);
    }

    return result
}

function hexDecode(text) {
    var j;
    var hexes = text.match(/.{1,4}/g) || [];
    var back = "";
    for(j = 0; j<hexes.length; j++) {
        back += String.fromCharCode(parseInt(hexes[j], 16));
    }

    return back;
}

const Card = props => {
    let theCard = String(props.value).split(".");        
    let suitIndex = 0;    
    let cardColor = "black";
    switch (theCard[1]) {
        case "1":
            //club
            suitIndex = 3;
            break;    
        case "2":
            //spade
            suitIndex = 0;
            break;
        case "3":
            //heart
            suitIndex = 1;
            cardColor = "red";
            break;
        case "4":
            //diamond
            suitIndex = 2;
            cardColor = "red";
            break;
        default:
            console.log("void");
            break;
    }
    
    const rankIndex = parseInt(theCard[0]) <= 11 ? parseInt(theCard[0]) : parseInt(theCard[0]) + 1;

    const codepoint = 0x1F0A0 + suitIndex * 0x10 + rankIndex;
	const tag = String.fromCodePoint(codepoint);    
    return <span className={cardColor}>{tag}</span>;
} 

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            game: playGame()
        }
    }

    render() {        
        const rows = this.state.game.map(function(play){            
            return <tr>
                <td>#{play.id}</td>
                <td><Card value={play.firstCard} /> <Card value={play.secondCard} /></td>                
                <td className="ibcard"><Card value={play.inBetweenCard} /></td>
                {play.didWin===true && (
                    <td className="win">Yes</td>    
                )}
                {play.didWin===false && (
                    <td>No</td>    
                )}
                <td>{play.chance}%</td>                
            </tr>;
        });
        
        return <table>
            <thead>
                <tr>
                    <th>Player</th>
                    <th>Cards</th>
                    <th>&nbsp;</th>
                    <th>Win</th>
                    <th>Chance</th>                    
                </tr>
            </thead>
            <tbody>
                {rows}
            </tbody>
        </table>;
    }
}

ReactDOM.render(<App />, document.getElementById('app'));