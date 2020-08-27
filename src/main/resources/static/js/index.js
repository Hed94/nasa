let userId = getUrlParameter('userId')
if(userId == null || userId ==''){
    userId = localStorage.getItem('userId')
    if(userId == null || userId ==''){
    document.getElementById('createUser').value = true
    }
    else{
    // fetch('/savedPreferences?userId='+userId)
    // .then(response => response.json())
    // .then(jsonResponse => console.log(jsonResponse))
        window.location.href = '/?userId='+userId
    }
}

if(userId != null || userId !=''){
    localStorage.setItem('userId',userId)
    document.getElementById('userId').value = userId
}

// Teoreticky by šlo používat jen id, kdyby se z toho jejich ID smazala marsApi ale takhle je to víc dynamické
let marsApiButtons = document.querySelectorAll("button[id*='marsApi']")
let apiData = document.getElementById('marsApiRoverData')
marsApiButtons.forEach(button => button.addEventListener('click',function(){
const buttonId = this.id;
const roverId = buttonId.split('marsApi')[1]
apiData.value = roverId;
document.getElementById('frmRoverType').submit()
}))


//Funkce pro sbírání hodnot z parametrů url
function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

let marsRoverType = document.getElementById('marsApiRoverData').value
highLightButton(marsRoverType)
let marsSol = document.getElementById('marsSol').value

function highLightButton(roverType)
{
    if(roverType =='')
    {
    roverType = 'Opportunity';
    }
    document.getElementById('marsApi'+roverType).classList.remove('btn-secondary')
    document.getElementById('marsApi'+roverType).classList.add('btn-primary')
    apiData.value = roverType;
}

$(document).ready(function() {

  const $valueSpan = $('.valueSpan2');
  const $value = $('#marsSol');
  $valueSpan.html($value.val());
  $value.on('input change', () => {

    $valueSpan.html($value.val());
  });
});
