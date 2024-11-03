const app = (() => {
    var nickname = "";
    var playerId = "";
    var api = apiClient;

    const login = async (newNickname) => {
        nickname = newNickname;
        try {
            const response = await api.createPlayer(nickname);
            playerId = response.id;
            console.log("Player created" + nickname);
            console.log(nickname.length);
            window.location.href = "../templates/salas.html";
        } catch (error) {
            console.error("Error al crear el jugador", error);
            alert(error.message);
        }
    };

    return {
        login
    };

})();