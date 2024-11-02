const app = (() => {
    var nickname = "";
    var playerId = "";
    var api = apiClient;

    const login = async (newNickname) => {
        nickname = newNickname;
        try {
            await api.createPlayer(nickname);
            console.log("Player created");
        } catch (error) {
            console.error("Error player already exists", error);
        }
    };

    return {
        login
    };

})();