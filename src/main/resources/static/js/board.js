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


    //MOVEMENTS LISTENERS
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

    const initializeBoard = async () => {
        try {
            const boardArray = await api.getGameSessionBoard("1"); // Esperar a que la promesa se resuelva
            console.log("BoardArray:", boardArray, "Type:", typeof boardArray);
            generateBoard(boardArray);
        } catch (error) {
            console.log("Error al obtener el tablero de la sesión:", error);
        }
    }

    const generateBoard = (boardArray) => {
        const gameBoard = document.getElementById('game-board');
        if (!gameBoard) {
            console.log("El elemento game-board no se encontró en el DOM");
            return;
        }

        // Limpiar el tablero antes de generarlo
        gameBoard.innerHTML = '';
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
                console.log('Dirección inválida:', direction);
                return;
        }
        let position = new Position(newPosX, newPosY)
        movePlayer(position)
        
    };

    const movePlayer = async (position) => {
        try {
            await api.movePlayer(session, nickname, position);
            return stompClient.send("/app/sessions/move." + session, {}); 
         } catch (error) {
            //alert("No se pudo realizar el movimiento. Por favor, inténtalo de nuevo.");
         }
    };

    //PLAYERS PANEL FUNCIONALITY

    const initializeGameSession = async () => {
        try {
            if (!nickname || !session) {
                console.error("Nickname o Game Session ID no encontrados.");
                return;
            }
            await updatePlayerList(session);
        } catch (error) {
            console.error("Error initializing game session:", error);
        }
    };

    const updatePlayerList = async (session) => {
        try {
            const players = await api.getPlayersInSession(session);
            const playerList = document.getElementById("player-list");

            const existingNicknames = Array.from(playerList.children).map(item => item.textContent);
            
            const newNicknames = players.map(player => player.nickname);
            const hasChanges = existingNicknames.length !== newNicknames.length ||
                !newNicknames.every(nickname => existingNicknames.includes(nickname));

            if (!hasChanges) return;
            playerList.innerHTML = "";
            players.forEach(player => {
                const listItem = document.createElement("li");
                listItem.textContent = player.nickname;
                playerList.appendChild(listItem);
            });
        } catch (error) {
            console.error("Error updating player list:", error);
        }
    };

    const exitFromGameSession = async () => {
        try {
            await stompClient.send("/app/sessions/enterOrExitSession." + session, {});
            unsubscribe();
            await api.removePlayerFromSession(session, nickname);
            sessionStorage.removeItem('session');
            window.location.href = "../templates/sessionMenu.html";
        } catch (error) {
            console.log("Error al salir de la sesión:", error);
        }
    };

    const enterSession = () => {
        return stompClient.send("/app/sessions/enterOrExitSession." + session, {}); 
    };
    //STOMP FUNCTIONS
    let stompClient = null;
    let subscription = null;

    const connectAndSubscribe = async function () {
        await new Promise((resolve, reject) => {
            let socket = new SockJS('/stompendpoint');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            subscription = stompClient.subscribe('/topic/sessions/' + session + "/move", function (eventbody) {
                initializeBoard();
            });

            subscription = stompClient.subscribe('/topic/sessions/' + session + "/updatePlayerList", function (eventbody) {
                updatePlayerList(session);
            });

            subscription = stompClient.subscribe('/topic/sessions/' + session + "/updateBoard", function (eventbody) {
                initializeBoard();
            });

            resolve();
            }, function (error) {
            reject(error);
            console.log("STOMP error");
            });
        });
    };

    const unsubscribe = () => {
        if (subscription !== null) {
            subscription.unsubscribe();
        }
        console.log("Unsubscribed from the gameSession Topic");
    };

    const initGameSession = async () => {
        connectAndSubscribe()
        .then(() => initializeGameSession())
        .then(() =>enterSession());
    };


    return {
        init: function(){
            initGameSession();
        },
        createPositionFromMovement,
        movePlayer,
        initializeBoard,
        exitFromGameSession,
        initializeGameSession
    };

})();

board.init();