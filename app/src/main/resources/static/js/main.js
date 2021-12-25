async function subscribeStartUpEvents() {
    const startUpToast = document.getElementById('startUpToast');

    const source = new EventSource('/api/startUp/events');
    source.onmessage = async function (event) {
        console.log(event.data);
        const eventData = JSON.parse(event.data);
        if (startUpToast) {
            const toastMessage = document.getElementById('toastMessage');
            toastMessage.innerText = eventData.name + ' status ' + eventData.payload;
            const toast = new bootstrap.Toast(startUpToast);
            toast.show();
        }
    };
}

async function getStartUpEvents() {
    fetch('api/startUp/allEvents').then(async (data) => {
        console.log(await data.json());
    });
}

async function getBotStatus() {
    fetch('api/startUp/botOnline').then(async (data) => {
        const fetchData = await data.json()
        console.log(fetchData);
        const element = document.getElementById('botStatus');
        if(fetchData) {
            element.innerHTML = '<span class="badge bg-success">Online</span>'
        } else {
            element.innerHTML = '<span class="badge bg-danger">Offline</span>'
        }
        const botStartButton = document.getElementById('botStartButton');
        if(fetchData) {
            botStartButton.value = 'Restart Bot'
        } else {
            botStartButton.value = 'Start Bot'
        }
    });
}

getStartUpEvents();

getBotStatus()
const intervalId = window.setInterval(function () {
    getBotStatus();
}, 5000);