var gulp = require('gulp');
var cssnano = require('gulp-cssnano');
var uglify = require('gulp-uglify');
var imagemin = require('gulp-imagemin');
var cache = require('gulp-cache');
var autoprefixer = require('gulp-autoprefixer');
var browserSync = require('browser-sync').create();
var del = require('del');
var runSequence = require('run-sequence');
var useref = require('gulp-useref');
var gulpIf = require('gulp-if');
var versionNo = require('gulp-version-number');

//Browser definitions for autoprefixer 
var AUTOPREFIXER_BROWSERS = [
	'last 3 versions',
	'ie >= 8',
	'ios >=7',
	'android >=4.4',
	'bb >=10'
];

//setting version number to css and js files
var versionConfig = {
	'value': '%DT%',
	'append': {
		'key': 'v',
		'to': ['css', 'js'],
	},
};

//browserSync for live-reload and server
gulp.task('browserSync', function () {
	browserSync.init({
		server: {
			baseDir: 'src'
		}
	});
});

//===== DEVELOPMENT =====
//watch for changes
gulp.task('watch', ['browserSync'], function () {
	gulp.watch('src/*.html', browserSync.reload)
	gulp.watch('src/css/*.css', browserSync.reload)
	gulp.watch('src/js/*.js', browserSync.reload)
});

//default task for development
gulp.task('default', function (callback) {
	runSequence(['browserSync', 'watch'], callback)
});


// ===== PRODUCTION =====
//clean dist folder
gulp.task('clean:dist', function () {
	del.sync('dist')
});

//html files -> cssnano -> uglify -> versionNo
gulp.task('useref', function () {
	gulp.src('src/*.html')
		.pipe(useref())
		.pipe(gulpIf('*.css', cssnano({
			autoprefixer: {
				browsers: AUTOPREFIXER_BROWSERS,
				add: true
			}
		})))
		.pipe(gulpIf('*.js', uglify().on('error', function (e) {
			console.log(e);
		})))
		.pipe(versionNo(versionConfig))
		.pipe(gulp.dest('dist'))
});

//imagemin
gulp.task('imagemin', function () {
	gulp.src('src/images/**/*.+(png|svg|jpg|jpeg)')
		.pipe(cache(imagemin({ interlaced: true })))
		.pipe(gulp.dest('dist/images'))
});

gulp.task('copy', function () {
	  gulp.src(['./src/robots.txt'])
		    .pipe(gulp.dest('dist'));
});

//build for production
gulp.task('build', function (callback) {
	runSequence('clean:dist', ['useref', 'imagemin'], 'copy', callback)
});
