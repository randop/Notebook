const server = require('net').createServer().listen();
const fs = require("fs");
const util = require("util");
const readDir = util.promisify(fs.readdir);
const sizeOf = util.promisify(require('image-size'));

const PDFDocument = require('pdfkit');

// Create a document
const doc = new PDFDocument();

const mmm = require('mmmagic'), Magic = mmm.Magic;
const magic = new Magic(mmm.MAGIC_MIME_TYPE);

const detectFileType = (file) => {
	return new Promise( (resolve, reject) => {
		magic.detectFile(file, function(err, result) {
			if (err) {
				return reject(err);
			}
			return resolve(result);
		});	
	});
};

(async () => {
	try {
		fs.unlinkSync("output.pdf");
	} catch (err) {
		//void
	}

	// Create a document
	const doc = new PDFDocument({
		autoFirstPage: false
	});	
	doc.pipe(fs.createWriteStream('output.pdf'));		
	
	const files = await readDir("./pics");
	const filesLength = files.length;
	for(let i = 0; i < filesLength;i++) {		
		const file = files[i];
		const fileType = await detectFileType(`./pics/${file}`);
		if (fileType==="image/png" || fileType==="image/jpg" || fileType==="image/jpeg") {
			const dimensions = await sizeOf(`./pics/${file}`);
			if (dimensions.orientation) {
				doc.addPage({layout: 'landscape'});	
			} else {
				doc.addPage();
			}
			const PORTRAIT_MAX_WIDTH = doc.page.width;
			const PORTRAIT_MAX_HEIGHT = doc.page.height;
			console.log(dimensions, file);
			
			doc.image(`./pics/${file}`, 0, 0, {
			  fit: [doc.page.width, doc.page.height], 			  
			  align: 'center',
			  valign: 'center'
			});
			await doc.flushPages();
		}
	}

	await doc.end();
	console.log("done");
	server.close();
})();


/*
const callback = ffi.Callback('void', [], function() {
  // do your stuff here

  // switch stream out of flowing mode.
  process.stdin.pause();
});

// set stream into flowing mode ("old mode")
process.stdin.resume();

lib.run_delayed(callback);
*/