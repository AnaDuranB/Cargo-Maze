const sessionMenu = (() => {
    let nickname = sessionStorage.getItem('nickname');
    let api = apiClient;
    var stompClient = null;
    var subscription = null;

    const enterSession = async (sessionId) => {
        try {
            if (!nickname || nickname.length === 0) {
                alert("No se ha ingresado un nickname");
                return;
            }
            await api.enterSession(sessionId, nickname);
            sessionStorage.setItem('session', sessionId);

            window.location.href = "../templates/game.html";

        } catch (error) {
            console.log(error);
            alert("No se pudo ingresar a la sesión"); //hay que implementar la validacion si el usuario se sale de la sesion
                                                      // se debe eliminar de la lista de jugadores.
        }
    };

    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        sessionStorage.setItem('stompClient', stompClient); //guardar el stomp client para no instanciar diferentes clientes.
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            subscription = stompClient.subscribe('/topic/sessions', function (eventbody) {
                //Logica para manejar la cantidad de jugadores presentada.
            });   
        });
    };

    const initSessionMenu = () => {
        connectAndSubscribe();
    };

    const unsubscribe = () => {
        if (subscription !== null) {
            subscription.unsubscribe();
        }
        console.log("Unsubscribed from the gameSession Topic");
    };

    const connect = () => {
        if (subscription != null){
            subscription.unsubscribe();
            clearCanvas();
            connectAndSubscribe();
        }
    };

    return {
        enterSession,
        unsubscribe,
        connect,
        init : function () {
            initSessionMenu();
        },

    };

})();
