const board = (() => {

    class Position{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }


    const api = apiClient;
    const nickname = sessionStorage.getItem('nickname');
    const session = sessionStorage.getItem('session');

    document.addEventListener('keydown', (e) => {
        switch(e.key) {
            case 'a':
                createPositionFromMovement('LEFT');
                break;
            case 'd':
                createPositionFromMovement('RIGHT');
                break;
            case 'w':
                createPositionFromMovement('UP');
                break;
            case 's':
                createPositionFromMovement('DOWN');
                break;
        }
    });


    const updateGameBoard = async () => {
        try {
            const boardArray = await api.getGameSessionBoard("1"); // Esperar a que la promesa se resuelva
            console.log(boardArray);
            generateBoard(boardArray);
        } catch (error) {
            console.error("Error al obtener el tablero de la sesión:", error);
        }
    }

    const generateBoard = (boardArray) => {
        const gameBoard = document.getElementById('game-board');
        if (!gameBoard) {
            console.error("El elemento game-board no se encontró en el DOM");
            return;
        }

        // Limpiar el tablero antes de generarlo
        gameBoard.innerHTML = '';

        console.log(typeof(boardArray));
        boardArray.forEach(row => {
            row.forEach(cell => {
                const cellDiv = document.createElement('li');
                cellDiv.classList.add('cell');

                switch (cell) {
                    case '.':
                        cellDiv.classList.add('empty');
                        // cellDiv.innerText = '⬛';
                        break;
                    case '#':
                        cellDiv.classList.add('wall');
                        cellDiv.innerText = '🧱';
                        break;
                    case 'B':
                        cellDiv.classList.add('box');
                        cellDiv.innerText = '📦'; 
                        break;
                    case 'T':
                        cellDiv.classList.add('goal');
                        cellDiv.innerText = '⭐'; 
                        break;
                    case 'P':
                        cellDiv.classList.add('player');
                        cellDiv.innerText = '😃';
                        break;
                }

                gameBoard.appendChild(cellDiv);
            });
        });
    }

    const createPositionFromMovement = async (direction) => {
        let newPosX = 0;
        let newPosY = 0;

        switch (direction) {
            case 'LEFT':
                newPosX -= 1;
                break;
            case 'RIGHT':
                newPosX += 1;
                break;
            case 'UP':
                newPosY -= 1;
                break;
            case 'DOWN':
                newPosY += 1;
                break;
            default:
                console.error('Dirección inválida:', direction);
                return;
        }
        let position = new Position(newPosX, newPosY)
        movePlayer(position)
        
    };

    const movePlayer = async (position) => {
        try {
            await api.movePlayer(session, nickname, position);
            return stompClient.send("/app/sessions/move." + session);	
         } catch (error) {
            alert("No se pudo realizar el movimiento. Por favor, inténtalo de nuevo.");
         }
    };


    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            subscription = stompClient.subscribe('/topic/sessions/' + session + "/move" , function () {
                initializeBoard();
            });
            
            subscription = stompClient.subscribe('/topic/sessions/' + session + "/update" , function (eventbody) {
                //update panels method
            });
        });

    };

    const initGameSession = () => {
        connectAndSubscribe();
    };


    return {
        init: function(){
            initGameSession();
        },
        createPositionFromMovement,
        movePlayer,
        updateGameBoard
    };

})();

board.init();