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
    public class DocNotificationsController : ControllerBase
    {
        private readonly EnrolleesDBContext _context;

        public DocNotificationsController(EnrolleesDBContext context)
        {
            _context = context;
        }

        // GET: api/DocNotifications
        [HttpGet]
        public IEnumerable<Doc_notifications> GetDoc_notifications()
        {
            return _context.Doc_notifications;
        }

        // GET: api/DocNotifications/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetDoc_notifications([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var doc_notifications = await _context.Doc_notifications.FindAsync(id);

            if (doc_notifications == null)
            {
                return NotFound();
            }

            return Ok(doc_notifications);
        }

        // PUT: api/DocNotifications/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutDoc_notifications([FromRoute] int id, [FromBody] Doc_notifications doc_notifications)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != doc_notifications.ID_Doc_notification)
            {
                return BadRequest();
            }

            _context.Entry(doc_notifications).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!Doc_notificationsExists(id))
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

        // POST: api/DocNotifications
        [HttpPost]
        public async Task<IActionResult> PostDoc_notifications([FromBody] Doc_notifications doc_notifications)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Doc_notifications.Add(doc_notifications);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetDoc_notifications", new { id = doc_notifications.ID_Doc_notification }, doc_notifications);
        }

        // DELETE: api/DocNotifications/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteDoc_notifications([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var doc_notifications = await _context.Doc_notifications.FindAsync(id);
            if (doc_notifications == null)
            {
                return NotFound();
            }

            _context.Doc_notifications.Remove(doc_notifications);
            await _context.SaveChangesAsync();

            return Ok(doc_notifications);
        }

        private bool Doc_notificationsExists(int id)
        {
            return _context.Doc_notifications.Any(e => e.ID_Doc_notification == id);
        }
    }
}