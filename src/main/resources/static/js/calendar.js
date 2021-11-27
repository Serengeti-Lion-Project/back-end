var date = new Date();

var renderCalendar = () => {
  var viewYear = date.getFullYear();
  var viewMonth = date.getMonth();

console.log(viewYear);
  // year-month 채우기
//   var hi = document.querySelector('.year-month');
//   if(hi){
//     textContent = viewYear;
//   }


  var year_month = document.getElementById("year-month");

  window.onload = function(){
  if(year_month.innerText){
          year_month.innerText = "new";
      }else{
          year_month.innerText = viewYear + '년 '+ (viewMonth + 1 )+ "월";
      }
  }

  // 지난 달 마지막 Date, 이번 달 마지막 Date
  var prevLast = new Date(viewYear, viewMonth, 0);
  var thisLast = new Date(viewYear, viewMonth + 1, 0);

  var PLDate = prevLast.getDate();
  var PLDay = prevLast.getDay();

  var TLDate = thisLast.getDate();
  var TLDay = thisLast.getDay();

  // Dates 기본 배열들
  var prevDates = [];
  var thisDates = [...Array(TLDate + 1).keys()].slice(1);
  var nextDates = [];

  // prevDates 계산
  if (PLDay !== 6) {
    for (let i = 0; i < PLDay + 1; i++) {
      prevDates.unshift(PLDate - i);
    }
  }

  // nextDates 계산
  for (let i = 1; i < 7 - TLDay; i++) {
    nextDates.push(i)
  }

  // Dates 합치기
  var dates = prevDates.concat(thisDates, nextDates);

  // Dates 정리
  var firstDateIndex = dates.indexOf(1);
  var lastDateIndex = dates.lastIndexOf(TLDate);
  dates.forEach((date, i) => {
    var condition = i >= firstDateIndex && i < lastDateIndex + 1
                      ? 'this'
                      : 'other';

                      let str = "<div class = \"date\"><span class = \"" + condition + "\">" + date + "</span></div>";
                      dates[i] = str;
                    })


  // Dates 그리기
//   var draw = document.querySelector('.dates')
//   if(draw)
//     document.innerHTML = dates.join('');

    // document.querySelector('.dates').innerHTML = dates.join('');

    var d = document.getElementById("dates");
    // var year_month = document.getElementById("year-month");

    console.log(dates.join)
    window.addEventListener('load' ,function(){


        if(d.innerText){
            d.innerText = "new";
        }else{
            // dates.innerText = dates.prototype.join.call(arguments);
            console.log(dates.join())

            d.innerHTML = dates.join();

        }
    });


  var today = new Date();
  if (viewMonth === today.getMonth() && viewYear === today.getFullYear()) {
    for (let date of document.querySelectorAll('.this')) {
      if (+date.innerText === today.getDate()) {
        date.classList.add('today');
        break;
      }
    }
  }
}

renderCalendar();

var prevMonth = () => {
    date.setMonth(date.getMonth() - 1);
    renderCalendar();
  }

  var nextMonth = () => {
    date.setMonth(date.getMonth() + 1);
    renderCalendar();
  }

  var goToday = () => {
    date = new Date();
    renderCalendar();
  }