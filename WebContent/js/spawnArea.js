const spawnBallByNumber = spawnAreaElement => {
    const spawnArea = document.querySelector(".spawn-area");
            
    let width = spawnArea.getBoundingClientRect().width;
    let height = spawnArea.getBoundingClientRect().height;

    //Div spawned size
    // 1/10 of width
    const maxRadius = width/10;
    // 1/50 of width
    const minRadius = width/50;

    //Spawn rect bound
    width -= maxRadius * 2;
    height -= maxRadius * 2;

    //Number of div to spawn
    const n = spawnArea.getAttribute("data-balls");

    //List of divs to spawn
    const spawnedDivs = [];

    //Prevent to block in loop
    let protection = 0;
    const PROTECTION_LEVEL = 10000;

    let totalArea = 0;

    while(spawnedDivs.length < n && protection <= PROTECTION_LEVEL) {
        const circle = {
            //Get a random position beetwen 0 and width
            x: Math.floor(Math.random() * width),
            //Get a random position beetwen 0 and height
            y: Math.floor(Math.random() * height),
            radius: Math.floor(Math.random() * (maxRadius - minRadius + 1) + minRadius)
        }

        let overlapping = false;
        for(let other of spawnedDivs) {
        	const x1 = (circle.x + circle.radius) / 2;
        	const x2 = (other.x + other.radius) / 2;
        	const y1 = (circle.y + circle.radius) / 2;
        	const y2 = (other.y + circle.radius) / 2;
        	
            const dist = distance(x1, x2, y1, y2);

            if(dist < circle.radius + other.radius) {
                //Overlapp
                overlapping = true;
                break;
            }
        }

        if(!overlapping) {
            spawnedDivs.push(circle);
            totalArea += circle.radius * circle.radius * 3.14;
            const size = circle.radius * 2;
            spawnArea.innerHTML += `<div class='spawned-bubble' style='top: ${circle.y}px; left: ${circle.x}px; width: ${size}px; height: ${size}px'></div>`;
        }

        protection++;
    }
    
    console.log(totalArea);
    console.log(spawnedDivs.length);
    spawnArea.setAttribute("data", totalArea.toFixed(2));
};

const spawnBallByArea = spawnAreaElement => {
    console.log("Reverse");

    const spawnArea = spawnAreaElement;
            
    let width = spawnArea.getBoundingClientRect().width;
    let height = spawnArea.getBoundingClientRect().height;

    //Div spawned size
    // 1/10 of width
    const maxRadius = width/10;
    // 1/50 of width
    const minRadius = width/50;

    //Spawn rect bound
    width -= maxRadius * 2;
    height -= maxRadius * 2;

    //Spawn area
    const totalArea = width * height;

    //List of divs to spawn
    const spawnedDivs = [];

    //Prevent to block in loop
    let protection = 0;
    const PROTECTION_LEVEL = 10000;

    let areaToOccupy = spawnArea.getAttribute('data-area');
    let areaOccuped = 0;

    if(!areaToOccupy || areaToOccupy > totalArea)
        areaToOccupy = totalArea;

    while(areaOccuped < areaToOccupy && protection <= PROTECTION_LEVEL) {
        const circle = {
            //Get a random position beetwen 0 and width
            x: Math.floor(Math.random() * width),
            //Get a random position beetwen 0 and height
            y: Math.floor(Math.random() * height),
            radius: Math.floor(Math.random() * (maxRadius - minRadius + 1) + minRadius)
        }

        let overlapping = false;
        for(let other of spawnedDivs) {
            const dist = distance(circle.x, other.x, circle.y, other.y);

            if(dist < circle.radius + other.radius) {
                //Overlapp
                overlapping = true;
                break;
            }
        }

        if(!overlapping) {
            spawnedDivs.push(circle);
            areaOccuped += circle.radius * circle.radius * 3.14;
            const size = circle.radius * 2;
            spawnArea.innerHTML += `<div class='spawned-bubble' style='top: ${circle.y}px; left: ${circle.x}px; width: ${size}px; height: ${size}px'></div>`;
        }

        protection++;
    }
    
    spawnArea.setAttribute("data-area", areaToOccupy);
}

const distance = (x1, x2, y1, y2) => {
    const a = x1 - x2;
    const b = y1 - y2;

    return Math.sqrt(a*a + b*b);
}