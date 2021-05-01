$(document).ready(function(){

  var $messagesBox = $( ".messages-box" );
  $('.message-icon').click(function(){
    $('.chat').fadeToggle(500);
  });

  var pseudo = ""
  while (pseudo == "") {
    pseudo = prompt("Veuillez donner votre pseudo", Math.round(new Date().getTime() + (Math.random() * 100)));
  }
  var exit = false;

  /////////// INSCRIPTION ///////////

  $.ajax({
    url: 'http://localhost:9991/api/room/'+pseudo,
    type: 'PUT',
    cache: false,
    timeout: 5000,
    success: function(data) {
      messagesBoxHeight = $messagesBox[0].scrollHeight,
      $messagesBox.append('<div class="message-container"><div class="sysmessage"><p> Vous vous êtes bien inscrit au salon</p></div></div>');
      $messagesBox.scrollTop( messagesBoxHeight );
    },
    error: function(jqXHR, textStatus, errorThrown) {
      alert("Une erreur s'est produite lors de votre inscription ");
      console.log(jqXHR);
      console.log(textStatus);
      console.log(errorThrown);

    }
  });


//////////// RECUPARATION DES MESSAGE//////////////
  setInterval(function() {
    if(!exit){
      $.ajax({
        url: 'http://localhost:9991/api/room/'+pseudo,
        dataType: "text",
        type: 'GET',
        cache: false,
        timeout: 5000,
        success: function(data) {
          var jsonData = jQuery.parseJSON(data);
          if(jsonData != null){
            messagesBoxHeight = $messagesBox[0].scrollHeight,
            jsonData.forEach(element => {
              let pseudoMessage = element.expeditor;
              let message = element.content;
              if (pseudoMessage == "" && message && message.length>0) {
                $messagesBox.append('<div class="message-container"><div class="sysmessage"><p>'+message+'</p></div></div>');
              }
              else if(message && message.length>0) {
                $messagesBox.append('<div class="message-container"><div class="pmessage"> <p>'+pseudoMessage+'</p></div></div>');
                $messagesBox.append('<div class="message-container"><div class="inmessage"><i class="fa fa-close"></i> <p>'+message+'</p></div></div>');
              }
            });
            $messagesBox.scrollTop( messagesBoxHeight );
          }
        },
        error: function(jqXHR, textStatus, errorThrown) {
        }
      });
    }
  }, 10000);
  
  

  ////////////// ENVOI DES MESSAGES ///////////////////
  
  // form submit
  $('form').submit(function(e){
    e.preventDefault();
    if(!exit){
      message = $('input', this).val(),
      messageLength = message.length;
      var obj = {
        content: message
      }
      console.log(JSON.stringify(obj));
      if(messageLength > 0){
        $('input', this).removeClass('error');
        var that = this;
        $.ajax({
          url: 'http://localhost:9991/api/room/'+pseudo,
          data: JSON.stringify(obj),
          type: 'POST',
          cache: false,
          timeout: 5000,
          success: function(data) {
            messagesBoxHeight = $messagesBox[0].scrollHeight,
            $messagesBox.append('<div class="message-container"><div class="message"><i class="fa fa-close"></i> <p>' + message +'</p></div></div>');
            // scroll to see last message
            $messagesBox.scrollTop( messagesBoxHeight );
            $('input',that).val('');
            $('input',that).focus();
          },
          error: function(jqXHR, textStatus, errorThrown) {
            alert("Message non envoyé, une erreur s'est produite");
            console.log(jqXHR);
            console.log(textStatus);
            console.log(errorThrown)
          }
        });
      }else{
        $('input', this).addClass('error');
      }
    }else alert("Vous vous êtes déjâ déconnecté du salon");
  });  // form
  
  // delete massage
  $(document).on('click', '.fa-close', function(){ 
     $(this).parent().fadeOut(500,function(){
      $(this).remove();
      });
   }); 
  
  // mouse enter add class
  $(document).on('mouseenter', '.fa-close', function(){
    $(this).parent().addClass('active');
  });
  
  // mouse leave remove class
  $(document).on('mouseleave', '.fa-close', function(){
    $(this).parent().removeClass('active');
  });


  ///////////DESINSCRIPTION///////////////
  $(document).on('click', '.exit-icon', function(){ 
    if(!exit){
      let response = confirm("Voulez-vous vraiment quitter le salon ?");
      if(response){
        $.ajax({
          url: 'http://localhost:9991/api/room/'+pseudo,
          type: 'DELETE',
          cache: false,
          timeout: 5000,
          success: function(data) {
            messagesBoxHeight = $messagesBox[0].scrollHeight,
            $messagesBox.append('<div class="message-container"><div class="sysmessage"><p> Vous vous êtes désincrit du salon</p></div></div>');
            $messagesBox.scrollTop( messagesBoxHeight );
            exit = true;
            $('input').prop( "disabled", true );
            $('input').blur();
            
          },
          error: function(jqXHR, textStatus, errorThrown) {
            alert("Une erreur s'est produite lors de votre desinscription !");
          }
        });
      }
    }else alert("Vous vous êtes déjâ déconnecté du salon");

  }); 

  
});  // document ready