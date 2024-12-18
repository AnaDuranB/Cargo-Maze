const sessionMenu = (() => {
    let nickname = sessionStorage.getItem('nickname');
    let api = apiClient;
    let stompClient = null;
    let subscription = null;

    document.addEventListener('DOMContentLoaded', (event) => {
        sessionMenu.updateUserCount();
    });


    const enterSession = async (sessionId) => {
        try {
            if (!nickname || nickname.length === 0) {
                alert("No se ha ingresado un nickname");
                return;
            }
            await api.enterSession(sessionId, nickname);
            stompClient.send("/app/sessions", {}); 
            sessionStorage.setItem('session', sessionId);
            window.location.href = "../templates/game.html";
        } catch (error) {
            console.log(error);
            alert("No se pudo ingresar a la sesión");
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
        try {
            const currentUsers = await api.getPlayerCountInSession("1");
            const element = document.getElementById("capacity-1");
            if (element) {
                element.textContent = `${currentUsers}/4`;
            }
        } catch (error) {
            console.log(error);
        }

    };
    

    const unsubscribe = () => {
        if (subscription !== null) {
            subscription.unsubscribe();
        }
        console.log("Unsubscribed from the gameSession Topic");
    };

    return {
        enterSession,
        unsubscribe,
        init: function () {
            initSessionMenu();
        },
        updateUserCount
    };

})();

sessionMenu.init();