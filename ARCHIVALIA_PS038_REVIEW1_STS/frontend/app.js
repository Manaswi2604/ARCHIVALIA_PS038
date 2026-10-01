const API="http://localhost:8080";
const token=localStorage.getItem("archivalia_token");
if(!token) location.href="login.html";
const headers={"Authorization":"Bearer "+token,"Content-Type":"application/json"};
const toast=(s)=>{const t=document.getElementById("toast");t.textContent=s;t.style.display="block";setTimeout(()=>t.style.display="none",2200)};
async function getBooks(q=""){
 const r=await fetch(API+"/api/books"+(q?"?search="+encodeURIComponent(q):"")); return r.json();
}
function renderBooks(books){
 document.getElementById("bookCount").textContent=books.length;
 document.getElementById("booksGrid").innerHTML=books.map(b=>`
 <article class="book"><div><div class="cover">${(b.title||"A").slice(0,1)}</div>
 <h3>${b.title}</h3><p>${b.author}</p><span class="tag">${b.category||"Academic"}</span></div>
 <button class="primary" ${!b.available?"disabled":""} onclick="borrow(${b.id})">${b.available?"Borrow resource":"Currently borrowed"}</button></article>`).join("");
}
async function loadLoans(){
 const r=await fetch(API+"/api/borrow/my",{headers}); if(!r.ok)return;
 const data=await r.json();document.getElementById("loanCount").textContent=data.length;
 document.getElementById("loansList").innerHTML=data.length?data.map(x=>`
 <div class="loan"><div><b>Resource #${x.bookId}</b><p>${x.borrowDate} → due ${x.dueDate}</p></div>
 <div><span class="status">${x.status}</span>${x.status==="BORROWED"?` <button class="primary" onclick="returnBook(${x.id})">Return</button>`:""}</div></div>`).join(""):"<p style='color:#6b746f'>No borrowing records yet.</p>";
}
async function borrow(id){
 const r=await fetch(API+"/api/borrow",{method:"POST",headers,body:JSON.stringify({bookId:id,loanDays:7})});
 const d=await r.json(); if(!r.ok){toast(d.message||"Borrow failed");return} toast("Resource borrowed successfully"); await refresh();
}
async function returnBook(id){
 const r=await fetch(API+"/api/borrow/"+id+"/return",{method:"POST",headers});const d=await r.json();
 if(!r.ok){toast(d.message||"Return failed");return} toast("Returned • fine calculated");await refresh();
}
async function refresh(){renderBooks(await getBooks());loadLoans()}
document.getElementById("search").oninput=e=>getBooks(e.target.value).then(renderBooks);
document.getElementById("logout").onclick=()=>{localStorage.clear();location.href="login.html"};
refresh();
