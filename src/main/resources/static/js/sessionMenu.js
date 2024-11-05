const sessionMenu = (() => {
    let nickname = sessionStorage.getItem('nickname');
    let api = apiClient;

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

    return {
        enterSession
    };

})();
