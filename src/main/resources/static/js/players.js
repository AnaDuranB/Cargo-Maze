const players = (() => {
    let nickname = sessionStorage.getItem("nickname");
    console.log(nickname);
    let gameSessionId = sessionStorage.getItem("session");
    console.log(gameSessionId);

    const api = apiClient;

    const initializeGameSession = async () => {
        try {

            if (!nickname || !gameSessionId) {
                console.error("Nickname o Game Session ID no encontrados.");
                return;
            }
            await api.enterSession(gameSessionId, nickname);
            await updatePlayerList(gameSessionId);
        } catch (error) {
            console.error("Error initializing game session:", error);
        }
    };

    const updatePlayerList = async (gameSessionId) => {
        try {
            const players = await api.getPlayersInSession(gameSessionId);

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



    return { initializeGameSession };
})();
window.onload = players.initializeGameSession;
