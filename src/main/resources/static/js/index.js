/*
document.getElementById('curiousity').addEventListener('click',function(){
alert(this.id)
})
document.getElementById('opportunity').addEventListener('click',function(){
alert(this.id)
})
document.getElementById('spirit').addEventListener('click',function(){
alert(this.text)
})
//alert(roverId)
*/

// Množný způsob volání tlačítek místo individuálního způsobu výše
// Teoreticky by šlo používat jen id, kdyby se z toho jejich ID smazala marsApi ale takhle je to víc dynamické
let marsApiButtons = document.querySelectorAll("button[id*='marsApi']")
marsApiButtons.forEach(button => button.addEventListener('click',function(){
const buttonId = this.id;
const roverId = buttonId.split('marsApi')[1]
let apiData = document.getElementById('marsApiRoverData')
apiData.value = roverId;
document.getElementById('frmRoverType').submit()
}))

function getUrlParameter(name) {
    name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
    var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
    var results = regex.exec(location.search);
    return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};

const parameter = getUrlParameter("marsApiRoverData");
highLightButton(parameter)

function highLightButton(roverType)
{
    if(roverType =='')
    {
    roverType = 'marsApi';
    }
    document.getElementById('marsApi'+roverType).classList.remove('btn-secondary')
    document.getElementById('marsApi'+roverType).classList.add('btn-primary')
}

$(document).ready(function() {

  const $valueSpan = $('.valueSpan2');
  const $value = $('#customRange11');
  $valueSpan.html($value.val());
  $value.on('input change', () => {

    $valueSpan.html($value.val());
  });
});
