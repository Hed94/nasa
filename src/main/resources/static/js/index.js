// Teoreticky by šlo používat jen id, kdyby se z toho jejich ID smazala marsApi ale takhle je to víc dynamické
let marsApiButtons = document.querySelectorAll("button[id*='marsApi']")
let apiData = document.getElementById('marsApiRoverData')
marsApiButtons.forEach(button => button.addEventListener('click',function(){
const buttonId = this.id;
const roverId = buttonId.split('marsApi')[1]
apiData.value = roverId;
document.getElementById('frmRoverType').submit()
}))



function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

const parameterRoverName = getUrlParameter("marsApiRoverData")
highLightButton(parameterRoverName)
const parameterSol = getUrlParameter("marsSol")
document.getElementById('customRange11').value = parameterSol

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
  const $value = $('#customRange11');
  $valueSpan.html($value.val());
  $value.on('input change', () => {

    $valueSpan.html($value.val());
  });
});
