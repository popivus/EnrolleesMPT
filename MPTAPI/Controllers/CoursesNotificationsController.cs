using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MPTAPI.Data;
using MPTAPI.Models;

namespace MPTAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CoursesNotificationsController : ControllerBase
    {
        private readonly EnrolleesDBContext _context;

        public CoursesNotificationsController(EnrolleesDBContext context)
        {
            _context = context;
        }

        // GET: api/CoursesNotifications
        [HttpGet]
        public IEnumerable<Courses_notifications> GetCourses_notifications()
        {
            return _context.Courses_notifications;
        }

        // GET: api/CoursesNotifications/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetCourses_notifications([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var courses_notifications = await _context.Courses_notifications.FindAsync(id);

            if (courses_notifications == null)
            {
                return NotFound();
            }

            return Ok(courses_notifications);
        }

        // PUT: api/CoursesNotifications/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutCourses_notifications([FromRoute] int id, [FromBody] Courses_notifications courses_notifications)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != courses_notifications.ID_Courses_notification)
            {
                return BadRequest();
            }

            _context.Entry(courses_notifications).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!Courses_notificationsExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/CoursesNotifications
        [HttpPost]
        public async Task<IActionResult> PostCourses_notifications([FromBody] Courses_notifications courses_notifications)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Courses_notifications.Add(courses_notifications);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetCourses_notifications", new { id = courses_notifications.ID_Courses_notification }, courses_notifications);
        }

        // DELETE: api/CoursesNotifications/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCourses_notifications([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var courses_notifications = await _context.Courses_notifications.FindAsync(id);
            if (courses_notifications == null)
            {
                return NotFound();
            }

            _context.Courses_notifications.Remove(courses_notifications);
            await _context.SaveChangesAsync();

            return Ok(courses_notifications);
        }

        private bool Courses_notificationsExists(int id)
        {
            return _context.Courses_notifications.Any(e => e.ID_Courses_notification == id);
        }
    }
}