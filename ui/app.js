import { Scoreboard } from 'fwcboard/Scoreboard.java';

const scoreboard = new Scoreboard();
window.scoreboard = scoreboard; // Для отладки в консоли

const summaryElement = document.getElementById('summary');
const formStart = document.getElementById('form-start');
const formUpdate = document.getElementById('form-update');
const formFinish = document.getElementById('form-finish');

function renderMatches() {
    summaryElement.innerHTML = '';
    const matches = scoreboard.getSummary();

    if (matches.length === 0) {
        summaryElement.innerHTML = '<p>Нет активных матчей.</p>';
        return;
    }

    matches.forEach((match, index) => {
        const div = document.createElement('div');
        div.className = 'match';
        div.innerHTML = `
            <span>${index + 1}. ${match.toString()}</span>
            <input type="number" class="home-score" value="${match.homeScore}" data-id="${Array.from(scoreboard.matches.entries()).find(([_, m]) => m === match)[0]}" />
            <input type="number" class="away-score" value="${match.awayScore}" data-id="${Array.from(scoreboard.matches.entries()).find(([_, m]) => m === match)[0]}" />
            <button onclick="finishMatch(${Array.from(scoreboard.matches.entries()).find(([_, m]) => m === match)[0]})">Завершить</button>
        `;
        summaryElement.appendChild(div);
    });
}

window.finishMatch = function(matchId) {
    scoreboard.finishMatch(matchId);
    renderMatches();
};

formStart.addEventListener('submit', function(e) {
    e.preventDefault();
    const home = document.getElementById('home-team').value.trim();
    const away = document.getElementById('away-team').value.trim();
    try {
        scoreboard.startMatch(home, away);
        renderMatches();
        formStart.reset();
    } catch (err) {
        alert(err.message);
    }
});

formUpdate.addEventListener('submit', function(e) {
    e.preventDefault();
    const inputs = summaryElement.querySelectorAll('input');
    inputs.forEach(input => {
        const matchId = parseInt(input.getAttribute('data-id'));
        const homeScore = parseInt(summaryElement.querySelector(`input.home-score[data-id='${matchId}']`).value);
        const awayScore = parseInt(summaryElement.querySelector(`input.away-score[data-id='${matchId}']`).value);
        try {
            scoreboard.updateMatch(matchId, homeScore, awayScore);
        } catch (err) {
            console.error(err);
        }
    });
    renderMatches();
});