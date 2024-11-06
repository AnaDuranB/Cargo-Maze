const sessionMenu = (() => {
    let nickname = sessionStorage.getItem('nickname');
    let api = apiClient;
    let stompClient = null;
    let subscription = null;

    const enterSession = async (sessionId) => {
        try {
            if (!nickname || nickname.length === 0) {
                alert("No se ha ingresado un nickname");
                return;
            }
            stompClient.send("/app/sessions", {}); 
            await api.enterSession(sessionId, nickname);
            sessionStorage.setItem('session', sessionId);
            window.location.href = "../templates/game.html";

        } catch (error) {
            console.log(error);
            alert("No se pudo ingresar a la sesión"); //hay que implementar la validacion si el usuario se sale de la sesion
            // se debe eliminar de la lista de jugadores.
        }
    };

    let connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        let socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            subscription = stompClient.subscribe('/topic/sessions', function () {
                updateUserCount();
            });
        });
    };

    const initSessionMenu = () => {
        connectAndSubscribe();
    };

    const updateUserCount = async () => { //REALIZAR -> QUE ACTUALIZE SEGUN EL ID DE LA SESSION INCIADA
        const currentUsers = await api.getPlayerCountInSession("1");
        const element = document.getElementById("capacity-1");
        if (element) {
            element.textContent = `${currentUsers}/4`;
        }
    };
    

    const unsubscribe = () => {
        if (subscription !== null) {
            subscription.unsubscribe();
        }
        console.log("Unsubscribed from the gameSession Topic");
    };

    const connect = () => {
        if (subscription != null) {
            subscription.unsubscribe();
            clearCanvas();
            connectAndSubscribe();
        }
    };

    return {
        enterSession,
        unsubscribe,
        connect,
        init: function () {
            initSessionMenu();
        }
    };

})();

sessionMenu.init();